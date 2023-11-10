package com.inventory.inventory.controller;

import com.inventory.inventory.dto.InventoryDto;
import com.inventory.inventory.dto.ResponseDto;
import com.inventory.inventory.entity.Inventory;
import com.inventory.inventory.service.InventoryServiceImpl;
import com.inventory.inventory.util.VarList;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryServiceImpl inventoryServiceImpl;

    @Autowired
    private ResponseDto responseDto;

    @Autowired
    private ModelMapper modelMapper;

    //Insert item controller(For CRUD)
    @PostMapping(value = "/addItem")
    public ResponseEntity addItem(@RequestBody InventoryDto inventoryDto){
        try{
            String res = inventoryServiceImpl.addItem(inventoryDto);
            if(res.equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Item added successfully");
                responseDto.setContent(inventoryDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.equals("00")){
                responseDto.setCode(VarList.RSP_DUPLICATED);
                responseDto.setMessage("Item already exist");
                responseDto.setContent(inventoryDto);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Error");
                responseDto.setContent(inventoryDto);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_DUPLICATED);
            responseDto.setMessage("Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Update item controller(For CRUD)
    @PutMapping(value = "/updateItem")
    public ResponseEntity updateItem(@RequestBody InventoryDto inventoryDto){
        try{
            String res = inventoryServiceImpl.updateItem(inventoryDto);
            if(res.equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Item updated successfully");
                responseDto.setContent(inventoryDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Item not exist");
                responseDto.setContent(inventoryDto);
                return new ResponseEntity(responseDto, HttpStatus.NOT_FOUND);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Error");
                responseDto.setContent(inventoryDto);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_DUPLICATED);
            responseDto.setMessage("Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Fetch items controller(For CRUD)
    @GetMapping(value = "/fetchItem/{id}")
    public ResponseEntity fetchItem(@PathVariable(name = "id")Long id){
        try{
            Inventory res = inventoryServiceImpl.fetchItem(id);
            if(res == null) {
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Item not exist");
                responseDto.setContent("null");
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Item fetched successfully");
                responseDto.setContent(modelMapper.map(res, InventoryDto.class));
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Delete item controller(For Crud)
    @DeleteMapping(value = "/deleteItem/{id}")
    public ResponseEntity deleteItem(@PathVariable(name = "id")Long id){
        try{
             String res = inventoryServiceImpl.deleteItem(id);
            if(res.equals("02")) {
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Item not exist");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.NOT_FOUND);
            }else if(res.equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Item deleted successfully");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_DUPLICATED);
            responseDto.setMessage("Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Fetch all items
    @GetMapping(value = "/fetchAllItems")
    public ResponseEntity inventoryList(){
        List<Inventory> inventoryList = inventoryServiceImpl.fetchAllItems();
        responseDto.setCode(VarList.RSP_SUCCESS);
        responseDto.setMessage("Data Fetched");
        responseDto.setContent(inventoryList);
        return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
    }
}
