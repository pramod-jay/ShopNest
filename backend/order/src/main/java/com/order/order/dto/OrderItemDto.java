package com.order.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {
    private Long OrderItemId;
    private Long itemId;
    private Integer quantity;
    private Double itemPrice;
}
