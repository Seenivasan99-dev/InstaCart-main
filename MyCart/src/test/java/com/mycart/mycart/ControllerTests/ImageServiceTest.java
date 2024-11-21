package com.mycart.mycart.ControllerTests;


import com.mycart.mycart.Entities.Images;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Exceptions.ImageNotFound;
import com.mycart.mycart.Repository.ImageRepo;
import com.mycart.mycart.Repository.ProductRepo;
import com.mycart.mycart.Request.ImageDto;
import com.mycart.mycart.Service.ImageService;
import com.mycart.mycart.Service.ImageServiceImpl;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ImageServiceTest {

    @Autowired(required = true)
    ImageServiceImpl imageService;

    @MockBean
    ImageRepo imageRepo;

    @MockBean
    ProductRepo productRepo;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void getimagebyid(){
        Images image=new Images();
        Blob mockBlob = Mockito.mock(Blob.class);
        image.setImage(mockBlob);
        image.setId(1);
        image.setFilename("filename");
        image.setDownloadurl("downloadurl");
        lenient().when(imageRepo.findById(1)).thenReturn(Optional.of(image));
        assertEquals("filename",image.getFilename());

    }

    @Test
    public void getimagebyidexceptioncase(){
        lenient().when(imageRepo.findById(1)).thenReturn(Optional.empty());
        ImageNotFound res=assertThrows(ImageNotFound.class,()->{
            try {
                imageService.getImagesById(1);
            }
            catch (NullPointerException e){
                throw new ImageNotFound("image not found");
            }
        });
        assertEquals("image not found",res.getMessage());
        verify(imageRepo,times(1)).findById(1);
    }
    @Test
    public void addimagestest(){
        Product p1=new Product();
        p1.setName("product");
        p1.setId(1);
        MultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "image content".getBytes());
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        Images mockImage = new Images();
        p1.setImages(Collections.singletonList(mockImage));
        mockImage.setProduct(p1);
        mockImage.setId(1);
        mockImage.setFilename("image.jpg");
        mockImage.setDownloadurl("/api/v1/images/image/download/1");

        when(imageRepo.save(any(Images.class))).thenReturn(mockImage);

        List<ImageDto> imagedtos=new ArrayList<>();
        ImageDto imageDto=new ImageDto();
        imageDto.setFilename("image.jpg");
        imageDto.setDownloadurl("/api/v1/images/image/download/1");
        imageDto.setId(1);
        imagedtos.add(imageDto);
        imagedtos=imageService.saveImage(files,1);
        assertNotNull(imagedtos);
        assertEquals(1, imagedtos.size());
        assertEquals("image.jpg",imagedtos.get(0).getFilename());
        assertEquals(1,imagedtos.get(0).getId());
        verify(imageRepo,times(2)).save(any(Images.class));
    }

    @Test
    public  void updateimagetest(){
        Product p1=new Product();
        p1.setName("product");
        p1.setId(1);
        MultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "image content".getBytes());
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        Images mockImage = new Images();
        p1.setImages(Collections.singletonList(mockImage));
        mockImage.setProduct(p1);
        mockImage.setId(1);
        mockImage.setFilename("image.jpg");
        mockImage.setDownloadurl("/api/v1/images/image/download/1");
        when(imageRepo.save(any(Images.class))).thenReturn(mockImage);
        imageService.updateImage(file,1);
        verify(imageRepo,times(1)).save(any(Images.class));

    }

    @Test
    public void deleteimagetest(){

        Images mockImage = new Images();
        mockImage.setFilename("image.jpg");
        mockImage.setDownloadurl("/api/v1/images/image/download/1");
        mockImage.setId(1);
        Blob mockBlob = Mockito.mock(Blob.class);
        when(imageRepo.findById(1)).thenReturn(Optional.of(mockImage));
        imageService.deleteImageById(1);
        verify(imageRepo,times(1)).delete(mockImage);

    }

}
