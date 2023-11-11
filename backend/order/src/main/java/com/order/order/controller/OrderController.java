package com.order.order.controller;


import com.order.order.dto.*;
import com.order.order.entity.Order;
import com.order.order.service.OrderServiceImpl;
import com.order.order.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }else if(res.getRSU_code().equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Item id:"+res.getId()+" not found");
                responseDto.setContent(orderDto);
                return new ResponseEntity(responseDto, HttpStatus.NOT_FOUND);
            }else if(res.getRSU_code().equals("04")){
                responseDto.setCode(VarList.RSP_NOT_ENOUGH);
                responseDto.setMessage("Item id:"+res.getId()+" not enough quantity");
                responseDto.setContent(orderDto);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Server error");
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

    @GetMapping(value = "/fetchOrderById/{id}")
    public ResponseEntity fetchOrderById(@PathVariable(name = "id")Long id){
        try{
            OrderMsgDto res = orderServiceImpl.fetchOrderById(id);
            if(res.getRSU_CODE().equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Order not exist");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.NOT_FOUND);
            }else if(res.getRSU_CODE().equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Order fetched successfully");
                responseDto.setContent(res);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Server error");
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
}
