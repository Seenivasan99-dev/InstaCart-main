package com.mycart.mycart.Service;

import com.mycart.mycart.Entities.Images;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Exceptions.ImageNotFound;
import com.mycart.mycart.Repository.ImageRepo;
import com.mycart.mycart.Repository.ProductRepo;
import com.mycart.mycart.Request.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepo imgrepo;

    productServiceIImpl proserviceimpl;
    @Autowired
    private ImageRepo imageRepo;
    @Autowired
    private ProductRepo productRepo;

    @Override
    public Images getImagesById(int id) {
         return imgrepo.findById(id).orElseThrow(()->{
             throw new ImageNotFound("image not found");
         });


    }

    @Override
    public void deleteImageById(int id) {
            imgrepo.findById(id).ifPresentOrElse(imgrepo::delete,()->{throw new ImageNotFound("image not found");});
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> file, int id) {
        List<ImageDto> savedimages=new ArrayList<>();
        Product product=productRepo.getReferenceById(id);
        for(MultipartFile files:file) {

            try {
                Images images = new Images();
                images.setFiletype(files.getContentType());
                images.setFilename(files.getOriginalFilename());
                images.setImage(new SerialBlob(files.getBytes()));
                images.setProduct(product);
                String buiilddownloadurl="/api/v1/images/image/download/";
                String buildurl=buiilddownloadurl+images.getId();
                images.setDownloadurl(buildurl);
                Images savedimage=imgrepo.save(images);
                savedimage.setDownloadurl(buildurl+images.getId());
                imgrepo.save(savedimage);
                ImageDto dtoimage=new ImageDto();
                dtoimage.setFilename(savedimage.getFilename());
                dtoimage.setId(savedimage.getId());
                dtoimage.setDownloadurl(savedimage.getDownloadurl());
                savedimages.add(dtoimage);
            }
            catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        return savedimages;
    }

    @Override
    public void updateImage(MultipartFile file, int id) {
        try {
        Images image = new Images();
        image.setFilename(file.getOriginalFilename());
        image.setFiletype(file.getContentType());
        image.setImage(new SerialBlob(file.getBytes()));
        imageRepo.save(image);
        } catch (SQLException  | IOException e) {
            throw new RuntimeException(e);
        }


    }
}
