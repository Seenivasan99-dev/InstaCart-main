package com.mycart.mycart.Request;

import com.mycart.mycart.Entities.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;

@Data
public class ImageDto {
    private long id;
    private String filename;
    private String downloadurl;
}
