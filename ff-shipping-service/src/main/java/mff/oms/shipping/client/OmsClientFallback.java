package mff.oms.shipping.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ppsportal.dto.OrderDTO;

@Component
public class OmsClientFallback implements OmsClient {

	private Logger logger = LoggerFactory.getLogger(OmsClientFallback.class);

	@Override
	public OrderDTO getOrderById(String orderId) {
		OrderDTO fallbackOrder = new OrderDTO();
		fallbackOrder.setOrderId(orderId);
		fallbackOrder.setStatus("UNKNOWN");
		return fallbackOrder;
	}

	@Override
	public void updateOrderStatus(String orderId, String status) {
		logger.warn("OMS unavailable, cannot update status for order " + orderId);

	}

}
