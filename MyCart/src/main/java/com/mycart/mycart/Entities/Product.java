package com.mycart.mycart.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String brand;
    private double price;
    private int inventory;
    private String description;

    @OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Images> images;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="category_id")
    private Category category;

    public Product(int id, String name, String brand, double price, int inventory, String description, Category category) {
        this.id=id;
        this.name=name;
        this.brand=brand;
        this.price=price;
        this.inventory=inventory;
        this.description=description;
        this.category=category;
    }
}
