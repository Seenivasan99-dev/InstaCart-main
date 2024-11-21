package com.mycart.mycart.Dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderDto {

    private long id;
    private LocalDate orderdate;
    private double totalorderamount;
    private Set<OrderIteamDto> orderiteam;
    private String orderstatus;
    private Long userid;
}
