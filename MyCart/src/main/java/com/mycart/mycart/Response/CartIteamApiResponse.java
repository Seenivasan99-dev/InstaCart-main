package com.mycart.mycart.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartIteamApiResponse {
    private String message;
    private Object data;
}
