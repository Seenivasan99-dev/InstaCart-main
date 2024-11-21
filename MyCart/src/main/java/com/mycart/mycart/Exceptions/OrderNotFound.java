package com.mycart.mycart.Exceptions;

public class OrderNotFound extends RuntimeException{
    public OrderNotFound(String message){
        super(message);
    }
}
