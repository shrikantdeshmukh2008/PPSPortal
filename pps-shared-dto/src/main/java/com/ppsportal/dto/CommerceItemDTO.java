package com.ppsportal.dto;

import lombok.Data;

@Data
public class CommerceItemDTO {

	private String commerceItemId;
	private String productId;
	private Integer quantity;
	private double unitPrice;

	
}
