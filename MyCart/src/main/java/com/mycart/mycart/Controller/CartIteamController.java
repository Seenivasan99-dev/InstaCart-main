package com.mycart.mycart.Controller;

import com.mycart.mycart.Entities.Cart;
import com.mycart.mycart.Entities.User;
import com.mycart.mycart.Response.CartIteamApiResponse;
import com.mycart.mycart.Service.CartIteamImpl;
import com.mycart.mycart.Service.CartServiceImpl;
import com.mycart.mycart.Service.UserService;
import com.mycart.mycart.Service.UserServiceImpl;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RequestMapping("/Cartiteams")
@RestController
public class CartIteamController {

    @Autowired
    CartIteamImpl cartiteam;

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    UserService userService;

    @PostMapping("/addcart/{userid}")
    public ResponseEntity<CartIteamApiResponse>  addIteamtoCart(@RequestParam Integer productId,@RequestParam Integer quantity) {
        try {
            User user = userService.getAuthenicatedUser();
            Cart cart = cartService.intializenewCart(user);
            cartiteam.addIteamTocart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new CartIteamApiResponse("AddedSucessfully", null));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CartIteamApiResponse("Unauthorized", e.getMessage()));
        }

    }

    @DeleteMapping("/deletecart/{cartid}/{productId}")
    public ResponseEntity<CartIteamApiResponse> removieiteamfromCart(@PathVariable long cartid,@PathVariable int productId) {
        cartiteam.removeIteamTocart(cartid,productId);
        return ResponseEntity.ok(new CartIteamApiResponse("RemovedSucessfully",null));
    }

    @PutMapping("/updatecart/{cartid}/{productId}")
    public ResponseEntity<CartIteamApiResponse> updateIteamQuantity(@PathVariable long cartid,@PathVariable int productId,@RequestParam int quantity) {
        cartiteam.updateQuantity(cartid,productId,quantity);
        return ResponseEntity.ok(new CartIteamApiResponse("UpdatedSucessfully",null));
    }




}
