package com.inventory.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryDto {
    private Long item_id;
    private String item_name;
    private BigDecimal price;
    private String manufacturer;
    private String specifications;
    private Integer quantity;
}
