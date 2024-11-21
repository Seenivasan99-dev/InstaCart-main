package com.mycart.mycart.Request;

import com.mycart.mycart.Entities.Category;
import com.mycart.mycart.Entities.Images;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class AddProductRequest {
    private int id;
    private String name;
    private String brand;
    private double price;
    private int inventory;
    private String description;
    private Category category;
}
