package com.inventory.inventory.service;

import com.inventory.inventory.dto.InventoryDto;
import com.inventory.inventory.entity.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    public String addItem(InventoryDto inventoryDto);

    public String updateItem(InventoryDto inventoryDto);

    public Inventory fetchItem(Long id);

    public String deleteItem(Long id);

    public List<Inventory> fetchAllItems();
}
