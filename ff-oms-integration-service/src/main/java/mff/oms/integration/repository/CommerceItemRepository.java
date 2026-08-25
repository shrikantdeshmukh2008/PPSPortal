package mff.oms.integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mff.oms.integration.entity.CommerceItem;

@Repository
public interface CommerceItemRepository  extends JpaRepository<CommerceItem, String>{
	
	List<CommerceItem> findByOrderOrderId(String orderId);

}
