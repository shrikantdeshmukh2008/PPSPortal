package mff.oms.fulfillment.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InventoryClientFallback implements InventoryClient {

	private Logger logger = LoggerFactory.getLogger(InventoryClientFallback.class);

	@Override
	public void releaseReservedStock(String productId, Integer quantity) {
		logger.warn("Fallback: Inventory service unavailable, could not release reserved stock.");
	}

}
