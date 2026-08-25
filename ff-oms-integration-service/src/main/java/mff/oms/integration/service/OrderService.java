package mff.oms.integration.service;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mff.oms.integration.entity.CommerceItem;
import mff.oms.integration.entity.Order;
import mff.oms.integration.exception.InvalidOrderStateException;
import mff.oms.integration.exception.OrderNotFoundException;
import mff.oms.integration.repository.OrderRepository;

@Service
public class OrderService {
	
	private Logger logger=LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;

	public Order getOrderById(String orderId) {
		logger.debug("getOrderById method execution - start");		
		return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found...!!!"));
	}

	public Order saveOrder(Order order) {
		logger.debug("saveOrder method execution - start");
		String prefix = "MW";
		String orderNumber = prefix + generateOrderId();
		order.setOrderId(orderNumber);
		if(prefix.equalsIgnoreCase("MB")) {
			order.setOrderType("BOPIS");
		}else if(prefix.equalsIgnoreCase("MW")) {
			order.setOrderType("PPS");
		}

		if (order.getCommerceItems() != null) {
			for (CommerceItem item : order.getCommerceItems()) {
				String commerceItemId = "ci" + generateCommerceItemId();
				item.setCommerceItemId(commerceItemId);
				item.setOrder(order);
			}
		}
		return orderRepository.save(order);
	}

	public Order updateOrderStatus(String orderId, String status) {
		logger.debug("updateOrderStatus method execution - start");
		Order order = getOrderById(orderId);
		if ("PPS".equals(order.getOrderType())) {
			if (status.equals("PickedUp")) {
				throw new InvalidOrderStateException("PPS orders cannot be Picked Up");
			}
		} else if ("BOPIS".equals(order.getOrderType())) {
			if (status.equals("Shipped")) {
				throw new InvalidOrderStateException("BOPIS orders cannot be Shipped");
			}
		}
		order.setStatus(status);
		return orderRepository.save(order);
	}

	private long generateCommerceItemId() {
		logger.debug("generateCommerceItemId method execution - start");
		long min = 10_000_000_000L;
		long max = 100_000_000_000L; // Exclusive bound
		long random11DigitValue = ThreadLocalRandom.current().nextLong(min, max);
		return random11DigitValue;
	}

	private long generateOrderId() {
		logger.debug("generateOrderId method execution - start");
		long min = 10_000_00L;
		long max = 100_000_00L; // Exclusive bound
		long random7DigitValue = ThreadLocalRandom.current().nextLong(min, max);
		return random7DigitValue;
	}
}
