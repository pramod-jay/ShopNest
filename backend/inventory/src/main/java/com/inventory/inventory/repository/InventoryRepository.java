package com.inventory.inventory.repository;

import com.inventory.inventory.dto.ItemNamePriceDto;
import com.inventory.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query(value = "SELECT * FROM inventorydb.item a WHERE a.item_id = ?1", nativeQuery = true)
    Inventory fetchItemNamePrice(Long id);
}
