package com.mycart.mycart.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartIteam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private int quantity;
    private long unitPrice;
    private long totalPrice;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    @JsonIgnore
    private Cart cart;

    public void setTotalAmnount(){
        this.totalPrice=this.unitPrice*this.quantity;
    }

}
