package com.mycart.mycart.Exceptions;

public class NoAvailableProducts extends RuntimeException{
    public NoAvailableProducts(String message){
        super(message);
    }
}
