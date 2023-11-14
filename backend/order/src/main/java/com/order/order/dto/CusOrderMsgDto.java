package com.order.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CusOrderMsgDto {
    private String RSU_Code;
    private Long cusId;
    private String name;
    private String address;
    private String tpNo;
    private String userName;
    private List<OrderMsgDto> orderMsgDtos;
}
