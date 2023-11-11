package com.order.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDto {
    private Long item_id;
    private String item_name;
    private Double price;
    private Integer quantity;
    private String manufacturer;
    private String specifications;
}
