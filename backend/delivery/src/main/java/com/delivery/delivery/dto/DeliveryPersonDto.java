package com.delivery.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryPersonDto {
    private Long id;
    private String name;
    private String tp_no;
    private Boolean availability;
    private String userName;
    private String password;
}
