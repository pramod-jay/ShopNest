package com.delivery.delivery.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DPWithoutPWDto {
    private String RSU_code;
    private Long id;
    private String name;
    private String tp_no;
    private Boolean availability;
    private String userName;
}
