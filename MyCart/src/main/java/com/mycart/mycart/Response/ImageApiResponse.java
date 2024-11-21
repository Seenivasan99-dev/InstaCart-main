package com.mycart.mycart.Response;

import com.mycart.mycart.Request.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
public class ImageApiResponse {
    private String message;
    private Object data;


}
