package com.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    private Long cusId;
    private String name;
    private String address;
    private String tpNo;
    private String userName;
    private String password;
}
