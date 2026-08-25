package mff.oms.fulfillment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mff.oms.fulfillment.entity.FulfillmentTask;

@Repository
public interface FulfillmentRepository extends JpaRepository<FulfillmentTask, Long> {
	
	
	List<FulfillmentTask> findByOrderId(String orderId);

}
