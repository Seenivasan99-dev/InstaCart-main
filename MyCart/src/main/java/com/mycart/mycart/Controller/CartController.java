package com.mycart.mycart.Controller;


import com.mycart.mycart.Entities.Cart;
import com.mycart.mycart.Exceptions.CartIteamNotFound;
import com.mycart.mycart.Response.CartApiResponse;
import com.mycart.mycart.Service.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    public CartServiceImpl cartservice;

    @GetMapping("/getcart/{cartid}")
    public ResponseEntity<CartApiResponse> getcart(@PathVariable long cartid){
        Cart cart = cartservice.getcart(cartid);
        return ResponseEntity.ok(new CartApiResponse("Sucesflly Fetched", cart));

    }

    @DeleteMapping("/clearcart/{cartid}")
    public ResponseEntity<CartApiResponse> clearcart(@PathVariable long cartid){
        cartservice.clearCart(cartid);
        return ResponseEntity.ok(new CartApiResponse("Sucesflly Cleared", null));
    }

    @GetMapping("/totalamount/cart/{cartid}")
    public ResponseEntity<CartApiResponse> getTotalAmount(@PathVariable long cartid){
        double totalprice=cartservice.TotalPrice(cartid);
        return ResponseEntity.ok(new CartApiResponse("Sucesflly Total Price", new BigDecimal(totalprice)));
    }





}
