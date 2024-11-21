package com.mycart.mycart.Dto;

import com.mycart.mycart.Entities.Order;
import com.mycart.mycart.Entities.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class OrderIteamDto {
    private Long Productid;
    private String ProductName;
    private int quantity;
    private double price;
}
