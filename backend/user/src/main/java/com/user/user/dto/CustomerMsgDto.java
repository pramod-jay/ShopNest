package com.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerMsgDto {
    private String RES_Code;
    private Long cusId;
    private String name;
    private String address;
    private String tpNo;
    private String userName;
}
