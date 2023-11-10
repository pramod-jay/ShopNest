package com.order.order.service;

import com.order.order.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Order saveOrder(Order order);

    List<Order> fetchOrder();

    Order updateOrder(Order order, Long orderId);

    void deleteOrderById(Long orderId);

}
