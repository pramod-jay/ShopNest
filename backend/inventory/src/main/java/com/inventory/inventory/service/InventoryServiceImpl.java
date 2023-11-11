package com.inventory.inventory.service;

import com.inventory.inventory.dto.InventoryDto;
import com.inventory.inventory.dto.ItemNamePriceDto;
import com.inventory.inventory.entity.Inventory;
import com.inventory.inventory.repository.InventoryRepository;
import com.inventory.inventory.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    ItemNamePriceDto itemNamePriceDto = new ItemNamePriceDto();


    //Insert item method
    @Override
    public String addItem(InventoryDto inventoryDto){
        if(inventoryRepository.existsById(inventoryDto.getItem_id())){
            return VarList.RSP_DUPLICATED;
        }else{
            inventoryRepository.save(modelMapper.map(inventoryDto, Inventory.class));
            return VarList.RSP_SUCCESS;
        }
    }

    //Update item method
    @Override
    public String updateItem(InventoryDto inventoryDto){
        if(inventoryRepository.existsById(inventoryDto.getItem_id())){
            inventoryRepository.save(modelMapper.map(inventoryDto, Inventory.class));
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NOT_EXIST;
        }
    }

    //Fetch item method
    @Override
    public Inventory fetchItem(Long id){
        if(inventoryRepository.existsById(id)){
            return inventoryRepository.getReferenceById(id);
        }else{
            return null;
        }
    }

    //Delete item method
    @Override
    public String deleteItem(Long id){
        if(inventoryRepository.existsById(id)){
            inventoryRepository.deleteById(id);
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NOT_EXIST;
        }
    }

    //Fetch all items
    @Override
    public List<Inventory> fetchAllItems(){
        return inventoryRepository.findAll();
    }

    //Fetch item name and price
    @Override
    public ItemNamePriceDto fetchItemNamePrice(Long id){
        Inventory res=inventoryRepository.fetchItemNamePrice(id);
        itemNamePriceDto.setItem_name(res.getItem_name());
        itemNamePriceDto.setPrice(res.getPrice());
        return itemNamePriceDto;
    }
}
