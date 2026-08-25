package mff.oms.fulfillment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppsportal.dto.OrderDTO;

@FeignClient(name = "ff-oms-integration-service", fallback = OmsClientFallback.class)
public interface OmsClient {

	@GetMapping("/oms/orders/{id}")
	OrderDTO getOrderById(@PathVariable("id") String id);

	@PutMapping("/oms/orders/{id}/status")
	void updateOrderStatus(@PathVariable("id") String id, @RequestParam("status") String status);
}
