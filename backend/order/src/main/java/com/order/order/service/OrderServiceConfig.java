package com.order.order.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderServiceConfig {

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl();
    }
}
