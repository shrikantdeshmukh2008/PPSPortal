package mff.oms.integration.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "oms_orders")
public class Order {

	@Id
	private String orderId;

	private Long customerId;
	private LocalDateTime orderDate;
	private String status;
	private double totalAmount;
	private String orderType; // PPS or BOPIS

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<CommerceItem> commerceItems = new ArrayList<>();

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<CommerceItem> getCommerceItems() {
		return commerceItems;
	}

	public void setCommerceItems(List<CommerceItem> commerceItems) {
		this.commerceItems = commerceItems;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
