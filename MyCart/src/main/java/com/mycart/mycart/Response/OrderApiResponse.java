package com.mycart.mycart.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderApiResponse {
    private String message;
    private Object data;
}
