package com.mycart.mycart.Request;


import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String LastName;
    private String email;
    private String password;

}
