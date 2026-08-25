package mff.oms.fulfillment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="ff-inventory-service",fallback = InventoryClientFallback.class)
public interface InventoryClient {

	@PutMapping("/inventory/release/{productId}")
    void releaseReservedStock(@PathVariable("productId") String productId,
                              @RequestParam("quantity") Integer quantity);
}
