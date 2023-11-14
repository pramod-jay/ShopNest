package com.order.order.controller;


import com.order.order.dto.*;

import com.order.order.service.OrderServiceImpl;
import com.order.order.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Autowired
    private ResponseDto responseDto;

    @PostMapping("/addOrder")
    public ResponseEntity saveOrder(@RequestBody OrderDto orderDto) {
        try {
            ItemMsgDto res = orderServiceImpl.addOrder(orderDto);
            if (res.getRSU_code().equals("01")) {
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Order has been created successfully");
                responseDto.setContent(orderDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            } else if (res.getRSU_code().equals("00")) {
                responseDto.setCode((VarList.RSP_DUPLICATED));
                responseDto.setMessage("Order already saved");
                responseDto.setContent(orderDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.getRSU_code().equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Item id:"+res.getId()+" not found");
                responseDto.setContent(orderDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.getRSU_code().equals("04")){
                responseDto.setCode(VarList.RSP_NOT_ENOUGH);
                responseDto.setMessage("Item id:"+res.getId()+" not enough quantity");
                responseDto.setContent(orderDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Function error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Server Error: " + e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetchOrderById/{id}")
    public ResponseEntity fetchOrderById(@PathVariable(name = "id")Long id){
        try{
            OrderMsgDto res = orderServiceImpl.fetchOrderById(id);
            if(res.getRSU_CODE().equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Order not exist");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.getRSU_CODE().equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Order fetched successfully");
                responseDto.setContent(res);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Function error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Server Error: " + e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Fetch all the orders of specific customer
    @GetMapping("/fetchOrderByCustomer/{id}")
    public ResponseEntity fetchOrderByCustomer(@PathVariable(name = "id")Long id){
        try{
            CusOrderMsgDto res = orderServiceImpl.fetchOrderByCustomer(id);
            if(res.getRSU_Code().equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Customer not exist");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.getOrderMsgDtos().isEmpty()){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Customer does not have any orders");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Function error");
                responseDto.setContent(res);
                return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Server Error: " + e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Assign delivery person
    @GetMapping("/assignDeliveryPerson/{orderId}/{dpId}")
    public ResponseEntity assignDeliveryPerson(@PathVariable(name = "orderId")Long orderId, @PathVariable(name = "dpId")Long dpId){
        try{
            String res = orderServiceImpl.assignDeliveryPerson(orderId, dpId);
            if(res.equals("05")){
                responseDto.setCode(VarList.RSP_EXIST);
                responseDto.setMessage("Delivery person has assigned to this order already");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Delivery person successfully assigned to the order");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Function error");
                responseDto.setContent(res);
                return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Server Error: " + e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Fetch orders by Delivery Person
    @GetMapping("/fetchOrdersByDp/{dpId}")
    public ResponseEntity fetchOrdersByDp(@PathVariable(name = "dpId")Long dpId){
        try{
            List<Long> res = orderServiceImpl.fetchOrdersByDp(dpId);
            if(res.isEmpty()){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("This delivery person has not any order");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Customer id fetched successfully");
                responseDto.setContent(res);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }
        }catch (Exception e){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Server Error: " + e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
