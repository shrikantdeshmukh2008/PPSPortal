package mff.oms.inventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mff.oms.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	private Logger logger=LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	private InventoryService inventoryService;

	@PostMapping("/reserve/{orderId}")
	public ResponseEntity<String> reserveStock(@PathVariable String orderId) {
		logger.debug("reserveStock method execution - start");
		String result = inventoryService.reserveStock(orderId);
		logger.debug("reserveStock method execution - end");
		logger.info("reserveStock method execution completed..");
		return ResponseEntity.ok(result);
	}

	@PutMapping("/release/{productId}")
	public ResponseEntity<String> releaseReservedStock(@PathVariable String productId, @RequestParam Integer quantity) {
		logger.debug("releaseReservedStock method execution - start");
		String result = inventoryService.releaseReservedStock(productId, quantity);
		logger.debug("releaseReservedStock method execution - end");
		logger.info("releaseReservedStock method execution completed..");
		return ResponseEntity.ok(result);
	}

}
