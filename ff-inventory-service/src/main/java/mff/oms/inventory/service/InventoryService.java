package mff.oms.inventory.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppsportal.dto.CommerceItemDTO;
import com.ppsportal.dto.OrderDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import mff.oms.inventory.client.OmsClient;
import mff.oms.inventory.entity.InventoryItem;
import mff.oms.inventory.exception.InsufficientInventoryException;
import mff.oms.inventory.repository.InventoryRepository;

@Service
public class InventoryService {

	private Logger logger = LoggerFactory.getLogger(InventoryService.class);
	@Autowired
	private InventoryRepository inventoryRepo;

	@Autowired
	private OmsClient omsClient; // Feign client to call OMS

	@CircuitBreaker(name = "ff-oms-integration-service", fallbackMethod = "reserveStockFallback")
	@Retry(name = "ff-oms-integration-service")
	public String reserveStock(String orderId) {
		logger.debug("reserveStock method execution - start");
		// Step 1: Fetch order from OMS
		OrderDTO order = omsClient.getOrderById(orderId);

		// Step 2: Check inventory for each item
		for (CommerceItemDTO item : order.getItems()) {
			InventoryItem inv = inventoryRepo.findByProductId(item.getProductId())
					.orElseThrow(() -> new InsufficientInventoryException("Product not found in inventory"));

			if (inv.getAvailableQuantity() < item.getQuantity()) {
				throw new InsufficientInventoryException("Insufficient stock for product " + item.getProductId());
			}

			// Deduct stock,increase reserved
			inv.setAvailableQuantity(inv.getAvailableQuantity() - item.getQuantity());
			inv.setReservedQuantity(inv.getReservedQuantity() + item.getQuantity());
			inv.setLastUpdated(LocalDateTime.now());
			inventoryRepo.save(inv);
		}

		// Step 3: Update OMS status
		omsClient.updateOrderStatus(orderId, "Reserved");

		return "Order " + orderId + " stock reserved successfully";
	}

	public String releaseReservedStock(String productId, Integer quantity) {
		logger.debug("releaseReservedStock method execution - start");
		InventoryItem item = inventoryRepo.findByProductId(productId)
				.orElseThrow(() -> new InsufficientInventoryException("Product not found"));

		if (item.getReservedQuantity() < quantity) {
			throw new InsufficientInventoryException("Not enough reserved stock to release");
		}

		item.setReservedQuantity(item.getReservedQuantity() - quantity);
		item.setLastUpdated(LocalDateTime.now());
		inventoryRepo.save(item);

		return "Released " + quantity + " units of " + productId;
	}

	public String reserveStockFallback(String orderId, Throwable t) {
		return "Fallback: Could not reserve stock for order " + orderId + " because OMS is unavailable.";
	}
}
