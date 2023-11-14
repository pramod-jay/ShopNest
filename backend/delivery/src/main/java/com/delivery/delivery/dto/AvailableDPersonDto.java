package com.delivery.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AvailableDPersonDto {
    private String rsu_code;
    private List<String> dPersons;
}
