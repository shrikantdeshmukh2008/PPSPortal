package mff.oms.shipping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mff.oms.shipping.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
	
	List<Shipment> findByOrderId(String orderId);

}
