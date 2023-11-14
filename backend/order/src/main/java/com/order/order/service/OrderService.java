package com.order.order.service;

import com.order.order.dto.CusOrderMsgDto;
import com.order.order.dto.ItemMsgDto;
import com.order.order.dto.OrderDto;
import com.order.order.dto.OrderMsgDto;
import com.order.order.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface OrderService {
    ItemMsgDto addOrder(OrderDto orderDto);

    OrderMsgDto fetchOrderById(Long cusId);

    CusOrderMsgDto fetchOrderByCustomer(Long cusId);

    String assignDeliveryPerson(Long orderId, Long dpId);

    List<Long> fetchOrdersByDp(Long dpId);
}

