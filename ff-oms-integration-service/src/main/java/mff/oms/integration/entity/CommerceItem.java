package mff.oms.integration.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "oms_commerce_items")
public class CommerceItem {

	@Id
	private String commerceItemId;
	private String productId;
	private Integer quantity;
	private double unitPrice;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getCommerceItemId() {
		return commerceItemId;
	}

	public void setCommerceItemId(String commerceItemId) {
		this.commerceItemId = commerceItemId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
