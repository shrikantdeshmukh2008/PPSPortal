package mff.oms.inventory.client;

import org.springframework.stereotype.Component;

import com.ppsportal.dto.OrderDTO;

@Component
public class OmsClientFallback implements OmsClient {

	@Override
	public OrderDTO getOrderById(String orderId) {

		OrderDTO fallbackOrder = new OrderDTO();
		fallbackOrder.setOrderId(orderId);
		fallbackOrder.setStatus("UNKNOWN");
		return fallbackOrder;
	}

	@Override
	public void updateOrderStatus(String orderId, String status) {
		System.out.println("OMS unavailable, cannot update status for order " + orderId);
	}

}
