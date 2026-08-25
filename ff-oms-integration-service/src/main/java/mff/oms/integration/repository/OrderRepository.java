package mff.oms.integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mff.oms.integration.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{

}
