package com.mycart.mycart.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestResponse {
    public  String message;
    public Object data;
}
