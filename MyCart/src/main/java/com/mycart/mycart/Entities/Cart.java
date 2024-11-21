package com.mycart.mycart.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double totalAmount;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "cart" ,cascade= CascadeType.ALL)
    private Set<CartIteam> cartiteam=new HashSet<CartIteam>();

    public double getTotalAmount() {
        this.totalAmount=0;
        double cartTotalPrice=this.cartiteam.stream().mapToDouble(iteam->iteam.getUnitPrice()*iteam.getQuantity()).sum();
        this.totalAmount+=cartTotalPrice;
        return totalAmount;
    }

    public void addIteam(CartIteam cartIteam) {
        this.cartiteam.add(cartIteam);
        cartIteam.setCart(this);
        getTotalAmount();
    }
    public void removeIteam(CartIteam cartIteam) {
        totalAmount=0;
        this.cartiteam.remove(cartIteam);
        cartIteam.setCart(null);
    }


}
