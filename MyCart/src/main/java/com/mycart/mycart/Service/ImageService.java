package com.mycart.mycart.Service;

import com.mycart.mycart.Entities.Images;
import com.mycart.mycart.Request.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

public interface ImageService {
    Images getImagesById(int id);
    void deleteImageById(int id);
    List<ImageDto> saveImage(List<MultipartFile> file, int id);
    void updateImage(MultipartFile file,int id);
}
