package mff.oms.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mff.oms.inventory.entity.InventoryItem;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, String> {
	Optional<InventoryItem> findByProductId(String productId);
}
