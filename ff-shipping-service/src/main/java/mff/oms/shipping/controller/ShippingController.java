package mff.oms.shipping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mff.oms.shipping.service.ShippingService;

@RestController
@RequestMapping("/shipping")
public class ShippingController {
	
	private Logger logger=LoggerFactory.getLogger(ShippingController.class);

	@Autowired
	private ShippingService shippingService;

	@PostMapping("/ship/{orderId}")
	public ResponseEntity<String> shipOrder(@PathVariable String orderId) {
		logger.debug("shipOrder method execution - start");
		String result = shippingService.shipOrder(orderId);
		logger.debug("shipOrder method execution - end");
		logger.info("shipOrder method execution completed..");
		return ResponseEntity.ok(result);
	}

}
