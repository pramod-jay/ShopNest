package com.order.order.service;

import com.order.order.dto.OrderDto;
import com.order.order.dto.OrderItemDto;
import com.order.order.dto.ResponseDto;
import com.order.order.entity.Order;
import com.order.order.entity.OrderItem;
import com.order.order.repository.OrderRepository;
import com.order.order.util.Uri;
import com.order.order.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    final Uri uri = new Uri();

    public String addOrder(OrderDto orderDto) {
        if (orderRepository.existsById(orderDto.getOrderID())) {
            return VarList.RSP_DUPLICATED;
        } else {
            List<OrderItem> orderItemList = orderDto.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                ResponseDto res = restTemplate.getForObject((uri.inventoryUri() + orderItem.getItemId().toString()), ResponseDto.class);
                if (res.getCode().equals("02")) {
                    return orderItem.getItemId().toString();
                }
                System.out.println(res);
            }
            orderRepository.save(modelMapper.map(orderDto, Order.class));
            return VarList.RSP_SUCCESS;
        }
    }

}
