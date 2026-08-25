package mff.oms.fulfillment.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppsportal.dto.CommerceItemDTO;
import com.ppsportal.dto.OrderDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import mff.oms.fulfillment.client.InventoryClient;
import mff.oms.fulfillment.client.OmsClient;
import mff.oms.fulfillment.entity.FulfillmentTask;
import mff.oms.fulfillment.exception.FulfillmentServiceException;
import mff.oms.fulfillment.repository.FulfillmentRepository;

@Service
public class FulfillmentService {

	private Logger logger = LoggerFactory.getLogger(FulfillmentService.class);

	@Autowired
	private FulfillmentRepository fulfillmentRepository;

	@Autowired
	private OmsClient omsClient; // to call OMS Service

	@Autowired
	private InventoryClient inventoryClient;

	@CircuitBreaker(name = "ff-oms-integration-service", fallbackMethod = "startFulfillmentFallback")
	@Retry(name = "ff-oms-integration-service")
	public String startFulfillment(String orderId) {

		logger.debug("startFulfillment method execution - start");
		// Step 1: Fetch order from OMS
		OrderDTO order = omsClient.getOrderById(orderId);
		logger.info("inside startFulfillment method - order",order.getOrderId());
		if (!"Reserved".equals(order.getStatus())) {
			throw new FulfillmentServiceException("Order is not ready for fulfillment");
		}

		// Step 2: Create fulfillment task
		FulfillmentTask task = new FulfillmentTask();
		task.setOrderId(orderId);
		if ("PPS".equals(order.getOrderType())) {
			task.setTaskStatus("Packed");
		} else if ("BOPIS".equals(order.getOrderType())) {
			task.setTaskStatus("ReadyForPickup");
		}

		task.setCreatedAt(LocalDateTime.now());
		fulfillmentRepository.save(task);

		// Step 3: Release reserved stock in Inventory
		for (CommerceItemDTO item : order.getItems()) {
			inventoryClient.releaseReservedStock(item.getProductId(), item.getQuantity());
		}

		// Step 4: Update OMS status
		if ("PPS".equals(order.getOrderType())) {
			omsClient.updateOrderStatus(orderId, "Packed");
			return "Order " + orderId + " packed for shipping";
		} else if ("BOPIS".equals(order.getOrderType())) {
			omsClient.updateOrderStatus(orderId, "ReadyForPickup");
			return "Order " + orderId + " ready for customer pickup";
		}
		return null;

	}

	public String startFulfillmentFallback(String orderId, Throwable t) {
		return "Fallback: Could not fulfill order " + orderId + " because OMS is unavailable.";
	}

}
