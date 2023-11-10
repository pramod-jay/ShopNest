package com.order.order.controller;


import com.order.order.dto.OrderDto;
import com.order.order.dto.ResponseDto;
import com.order.order.entity.Order;
import com.order.order.repository.OrderRepository;
import com.order.order.service.OrderServiceImpl;
import com.order.order.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

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
            String res = orderServiceImpl.addOrder(orderDto);
            if (res.equals("01")) {
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Order has been created successfully");
                responseDto.setContent(orderDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            } else if (res.equals("00")) {
                responseDto.setCode((VarList.RSP_DUPLICATED));
                responseDto.setCode("Order already saved");
                responseDto.setContent(orderDto);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Item id:"+res+" not found");
                responseDto.setContent(orderDto);
                return new ResponseEntity(responseDto, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            responseDto.setCode(VarList.RSP_DUPLICATED);
            responseDto.setMessage("Server Error: " + e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
