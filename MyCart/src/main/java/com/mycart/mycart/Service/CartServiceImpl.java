package com.mycart.mycart.Service;

import com.mycart.mycart.Entities.Cart;
import com.mycart.mycart.Entities.User;
import com.mycart.mycart.Exceptions.CartIdNotFound;
import com.mycart.mycart.Repository.CartIteamRepo;
import com.mycart.mycart.Repository.CartRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    public CartRepo cartRepo;
    @Autowired
    public CartIteamRepo cartIteamRepo;

    public AtomicLong counter = new AtomicLong(0);




    @Override
    public Cart getcart(long id) {
        Cart cart= Optional.ofNullable(cartRepo.findById(id)).orElseThrow(()-> {
            new  CartIdNotFound("No Cart of Id Found");
            return null;
        });
        cart.setTotalAmount(cart.getTotalAmount());
        return cartRepo.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(long id) {
        Cart cart= cartRepo.findById(id);
        cartIteamRepo.deleteAll(cart.getCartiteam());
        cart.getCartiteam().clear();
        cartRepo.deleteById(id);

    }

    @Override
    public double TotalPrice(long  id) {
        Cart cart= cartRepo.findById(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart getcarbyUserId(long id) {
        Cart cart=cartRepo.findCartByUserId(id);
        return cart;
    }

    public Cart intializenewCart(User user){

        return  Optional.ofNullable(getcarbyUserId(user.getId())).orElseGet(()->{
            Cart cart=new Cart();
            cart.setUser(user);
            return cartRepo.save(cart);
        });

    }

}
