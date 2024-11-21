package com.mycart.mycart.Service;

import com.mycart.mycart.Entities.Cart;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Exceptions.CartIteamNotFound;
import com.mycart.mycart.Repository.CartIteamRepo;
import com.mycart.mycart.Repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartIteamImpl implements CartIteam {

    @Autowired
    public ProductService productService;

    @Autowired
    public CartServiceImpl cartService;

    @Autowired
    public productServiceIImpl productServiceI;

    @Autowired
    public CartIteamRepo cartIteamRepo;

    @Autowired
    public CartRepo cartRepo;


    @Override
    public void addIteamTocart(long cartId, int productId, int quantity) {
        Cart cart=cartService.getcart(cartId);
        Product product=productService.findProductById(productId);
        com.mycart.mycart.Entities.CartIteam cartiteams=cart.getCartiteam().stream().filter(iteam-> iteam.getProduct().getId()==productId).findFirst().orElse(new com.mycart.mycart.Entities.CartIteam());
        if(cartiteams.getId()==0){
            cartiteams.setQuantity(quantity);
            cartiteams.setCart(cart);
            cartiteams.setProduct(product);
            cartiteams.setUnitPrice((long) product.getPrice());
        }
        else{
            cartiteams.setQuantity(cartiteams.getQuantity()+quantity);
        }
        cartiteams.setTotalAmnount();
        cart.addIteam(cartiteams);
        cartIteamRepo.save(cartiteams);
        cartRepo.save(cart);
    }

    @Override
    public void removeIteamTocart(long cartId, int productId) {
        Cart cart=cartService.getcart(cartId);
        com.mycart.mycart.Entities.CartIteam cartIteamtoRemove=cart.getCartiteam().stream().filter(iteam-> iteam.getProduct().getId()==productId).findFirst().orElse(null);
        cart.removeIteam(cartIteamtoRemove);
        cartRepo.save(cart);
    }

    @Override
    public void updateQuantity(long cartId, int productId, int quantity) {
        Cart cart=cartService.getcart(cartId);
        cart.getCartiteam().stream().filter(iteam-> iteam.getProduct().getId()==productId).findFirst().ifPresent(iteam->{
            iteam.setQuantity(quantity);
            iteam.setUnitPrice((long) iteam.getProduct().getPrice());
            iteam.setTotalAmnount();
        });

        double totalAmount=cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepo.save(cart);
    }
    public com.mycart.mycart.Entities.CartIteam getCartIteam(long cartId,int productId) {
        Cart cart=cartService.getcart(cartId);
        return cart.getCartiteam().stream().filter(iteam-> iteam.getProduct().getId()==productId).findFirst().orElseThrow(()->{
             new CartIteamNotFound("cart Iteam not found");
            return null;
        });
    }
}
