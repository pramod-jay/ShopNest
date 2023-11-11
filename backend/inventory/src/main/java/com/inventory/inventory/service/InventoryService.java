package com.inventory.inventory.service;

import com.inventory.inventory.dto.InventoryDto;
import com.inventory.inventory.dto.ItemNamePriceDto;
import com.inventory.inventory.entity.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    String addItem(InventoryDto inventoryDto);

    String updateItem(InventoryDto inventoryDto);

    Inventory fetchItem(Long id);

    String deleteItem(Long id);

    List<Inventory> fetchAllItems();

    ItemNamePriceDto fetchItemNamePrice(Long id);
}
