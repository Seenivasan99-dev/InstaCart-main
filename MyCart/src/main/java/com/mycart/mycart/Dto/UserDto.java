package com.mycart.mycart.Dto;

import com.mycart.mycart.Entities.Cart;
import com.mycart.mycart.Entities.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    private long id;
    private String firstName;
    private String LastName;
    private String email;
    private List<OrderDto> orderdto;
    private CartDto cartdto;
}
