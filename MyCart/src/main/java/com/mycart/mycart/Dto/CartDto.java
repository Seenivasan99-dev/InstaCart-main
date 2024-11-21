package com.mycart.mycart.Dto;

import com.mycart.mycart.Entities.CartIteam;
import com.mycart.mycart.Entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
public class CartDto {
    private long id;
    private long totalAmount;
    private Set<CartIteamDto> cartiteamdto=new HashSet<>();
}
