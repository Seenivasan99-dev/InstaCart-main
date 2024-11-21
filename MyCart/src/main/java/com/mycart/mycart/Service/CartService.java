package com.mycart.mycart.Service;

import com.mycart.mycart.Entities.Cart;

public interface CartService {
    Cart getcart(long id);
    void clearCart(long id);
    double TotalPrice(long id);
    Cart getcarbyUserId(long id);
}
