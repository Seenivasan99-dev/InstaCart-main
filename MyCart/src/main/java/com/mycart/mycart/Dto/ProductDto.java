package com.mycart.mycart.Dto;

import com.mycart.mycart.Entities.Category;
import com.mycart.mycart.Entities.Images;
import com.mycart.mycart.Request.ImageDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private int id;
    private String name;
    private String brand;
    private double price;
    private int inventory;
    private String description;
    private List<ImageDto> images;
    private Category category;
}
