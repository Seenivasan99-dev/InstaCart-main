package com.mycart.mycart.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductResponse {
    private String mesage;
    private Object data;
}
