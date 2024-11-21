package com.mycart.mycart.Service;

public interface CartIteam {
    void addIteamTocart(long cartId,int productId,int quantity);
    void removeIteamTocart(long cartId,int productId);
    void updateQuantity(long cartId,int productId,int quantity);
}
