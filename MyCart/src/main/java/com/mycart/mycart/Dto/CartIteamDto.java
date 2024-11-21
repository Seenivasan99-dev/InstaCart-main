package com.mycart.mycart.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycart.mycart.Entities.Cart;
import com.mycart.mycart.Entities.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class CartIteamDto {

    private long id;
    private ProductDto product;
    private int quantity;
    private long unitPrice;

}
