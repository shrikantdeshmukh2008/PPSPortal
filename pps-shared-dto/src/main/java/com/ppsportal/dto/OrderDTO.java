package com.ppsportal.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
	private String orderId;
	private Long customerId;
	private LocalDateTime orderDate;
	private String status;
	private double totalAmount;
	private List<CommerceItemDTO> items;
	private String orderType;

	
}
