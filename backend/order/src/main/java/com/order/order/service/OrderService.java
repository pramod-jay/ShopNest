package com.order.order.service;

import com.order.order.dto.ItemMsgDto;
import com.order.order.dto.OrderDto;
import com.order.order.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public ItemMsgDto addOrder(OrderDto orderDto);
    OrderDto getOrderById(Long orderID);
}

