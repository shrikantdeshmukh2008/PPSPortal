package mff.oms.fulfillment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mff.oms.fulfillment.service.FulfillmentService;

@RestController
@RequestMapping("/fulfillment")
public class FulfillmentController {
	private Logger logger=LoggerFactory.getLogger(FulfillmentController.class);

	@Autowired
	private FulfillmentService fulfillmentService;

	@PostMapping("/start/{orderId}")
	public ResponseEntity<String> startFulfillment(@PathVariable String orderId) {
		logger.debug("startFulfillment method execution - start");

		String result = fulfillmentService.startFulfillment(orderId);
		logger.debug("startFulfillment method execution - end");
		logger.info("startFulfillment method execution completed..");

		return ResponseEntity.ok(result);
	}

}
