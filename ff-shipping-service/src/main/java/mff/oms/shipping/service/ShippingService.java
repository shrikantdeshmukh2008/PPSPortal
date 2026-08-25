package mff.oms.shipping.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppsportal.dto.OrderDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import mff.oms.shipping.client.OmsClient;
import mff.oms.shipping.entity.Shipment;
import mff.oms.shipping.exception.ShippingServiceException;
import mff.oms.shipping.repository.ShipmentRepository;

@Service
public class ShippingService {

	private Logger logger = LoggerFactory.getLogger(ShippingService.class);

	@Autowired
	private ShipmentRepository shipmentRepository;

	@Autowired
	private OmsClient omsClient;

	@CircuitBreaker(name = "ff-oms-integration-service", fallbackMethod = "shipOrderFallback")
	@Retry(name = "ff-oms-integration-service")
	public String shipOrder(String orderId) {
		logger.debug("shipOrder method execution - start");
		// Step 1: Fetch order from OMS
		OrderDTO order = omsClient.getOrderById(orderId);

		if ("BOPIS".equals(order.getOrderType())) {
			return "Order " + orderId + " is BOPIS. Shipping not required. Awaiting customer pickup.";
		}

		if (!"Packed".equals(order.getStatus())) {
			throw new ShippingServiceException("Order is not ready for shipping");
		}

		// Step 2: Create shipment record
		Shipment shipment = new Shipment();
		shipment.setOrderId(orderId);
		shipment.setCarrier("BlueDart"); // hardcoded for demo
		shipment.setTrackingNumber("TRK-" + System.currentTimeMillis());
		shipment.setShippedAt(LocalDateTime.now());
		shipmentRepository.save(shipment);

		// Step 3: Update OMS status
		omsClient.updateOrderStatus(orderId, "Shipped");

		return "Order " + orderId + " shipped successfully with tracking " + shipment.getTrackingNumber();
	}

	public String shipOrderFallback(String orderId, Throwable t) {
		return "Fallback: Could not ship order " + orderId + " because OMS is unavailable.";
	}

}
