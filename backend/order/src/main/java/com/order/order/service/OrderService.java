package com.order.order.service;

import com.order.order.dto.ItemMsgDto;
import com.order.order.dto.OrderDto;
import com.order.order.dto.OrderMsgDto;
import com.order.order.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface OrderService {
    public ItemMsgDto addOrder(OrderDto orderDto);

    public OrderMsgDto fetchOrderById(Long cusId);

}

