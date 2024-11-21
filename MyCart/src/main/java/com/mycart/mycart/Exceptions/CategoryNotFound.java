package com.mycart.mycart.Exceptions;

public class CategoryNotFound extends RuntimeException{
    public CategoryNotFound(String message){
        super(message);
    }
}
