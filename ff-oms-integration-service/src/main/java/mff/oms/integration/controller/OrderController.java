package mff.oms.integration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ppsportal.dto.OrderDTO;

import mff.oms.integration.entity.Order;
import mff.oms.integration.mapper.OrderMapper;
import mff.oms.integration.service.OrderService;

@RestController
@RequestMapping("/oms")
@RefreshScope
public class OrderController {

	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<OrderDTO> getOrder(@PathVariable String orderId) {
		logger.debug("getOrder method execution - start");
		Order order = new Order();
		try {
			order = orderService.getOrderById(orderId);
			logger.debug("getOrder method execution - end");
			logger.info("getOrder method execution completed..");
		} catch (Exception ex) {
			logger.error("error in getOrder", ex);
		}
		return ResponseEntity.ok(OrderMapper.toDTO(order));
	}

	@PostMapping
	public ResponseEntity<OrderDTO> createOrder(@RequestBody Order pOrder) {
		logger.debug("createOrder method execution - start");
		Order order = new Order();
		try {
			order = orderService.saveOrder(pOrder);
			logger.debug("createOrder method execution - end");
			logger.info("createOrder method execution completed..");
		} catch (Exception ex) {
			logger.error("error in createOrder", ex);
		}
		return ResponseEntity.ok(OrderMapper.toDTO(order));
	}

	@PutMapping("/orders/{orderId}/status")
	public ResponseEntity<OrderDTO> updateStatus(@PathVariable String orderId, @RequestParam String status) {
		logger.debug("updateStatus method execution - start");
		Order order = new Order();
		try {
			order = orderService.updateOrderStatus(orderId, status);
			logger.debug("updateStatus method execution - end");
			logger.info("updateStatus method execution completed..");
		} catch (Exception ex) {
			logger.error("error in updateStatus", ex);
		}
		return ResponseEntity.ok(OrderMapper.toDTO(order));
	}

	@PutMapping("/orders/{orderId}/pickup")
	public ResponseEntity<OrderDTO> pickupOrder(@PathVariable String orderId) {
		logger.debug("pickupOrder method execution - start");
		Order order = new Order();
		try {
			order = orderService.updateOrderStatus(orderId, "PickedUp");
			logger.debug("pickupOrder method execution - end");
			logger.info("pickupOrder method execution completed..");
		} catch (Exception ex) {
			logger.error("error in pickupOrder", ex);
		}
		return ResponseEntity.ok(OrderMapper.toDTO(order));
	}

}
