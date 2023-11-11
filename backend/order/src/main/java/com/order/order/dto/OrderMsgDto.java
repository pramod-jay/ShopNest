package com.order.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderMsgDto {
    private String RSU_CODE;
    private Long orderID;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private Double total;
    private Long customerId;
    private List<OrderMsgListDto> orderMsgListDtoList;
}
