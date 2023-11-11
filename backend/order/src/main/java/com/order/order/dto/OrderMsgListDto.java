package com.order.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderMsgListDto {
    private Long itemId;
    private String itemName;
    private Double unitPrice;
    private Integer quantity;
    private Double totalPrice;
}
