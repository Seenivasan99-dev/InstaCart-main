package com.mycart.mycart.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryApiResponse {
    private String message;
    private Object data;
}
