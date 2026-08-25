package mff.oms.integration.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.ppsportal.dto.CommerceItemDTO;
import com.ppsportal.dto.OrderDTO;

import mff.oms.integration.entity.Order;

public class OrderMapper {

	public static OrderDTO toDTO(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setOrderId(order.getOrderId());
		dto.setCustomerId(order.getCustomerId());
		dto.setStatus(order.getStatus());
		dto.setTotalAmount(order.getTotalAmount());
		dto.setOrderDate(order.getOrderDate());
		dto.setOrderType(order.getOrderType());

		List<CommerceItemDTO> items = order.getCommerceItems().stream().map(item -> {
			CommerceItemDTO ci = new CommerceItemDTO();
			ci.setCommerceItemId(item.getCommerceItemId());
			ci.setProductId(item.getProductId());
			ci.setQuantity(item.getQuantity());
			ci.setUnitPrice(item.getUnitPrice());
			return ci;
		}).collect(Collectors.toList());

		dto.setItems(items);
		return dto;
	}
}
