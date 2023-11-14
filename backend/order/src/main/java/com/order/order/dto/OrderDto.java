package com.order.order.dto;

import com.order.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long orderID;
    private LocalTime time;
    private LocalDate date;
    private String status;
    private Double total;
    private Long customerId;
    private Long dp_id;
    private List<OrderItem> orderItemList;
}
