package com.mycart.mycart.Contollertests;


import com.mycart.mycart.Controller.ProductController;
import com.mycart.mycart.Dto.ProductDto;
import com.mycart.mycart.Entities.Category;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Response.ProductResponse;
import com.mycart.mycart.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductContollerTest {

    @InjectMocks
    public ProductController productController;

    @Mock
    public ProductService productService;



    @Test
    public void TestAllProductResponse(){
        Category dummyCategory = new Category();
        dummyCategory.setName("Dummy Category");
        dummyCategory.setId(1);
        dummyCategory.setProducts(null);
        List<Product> products = List.of(new Product(1, "Dummy Product", "Dummy Brand", 100.0, 50, "This is a dummy product for testing.", dummyCategory));
        ProductDto dtos=new ProductDto();
        dtos.setId(1);
        dtos.setName("Dummy Product");
        dtos.setBrand("Dummy Brand");
        dtos.setPrice(100.0);
        dtos.setInventory(50);
        dtos.setCategory(dummyCategory);
        dtos.setImages(null);
        List<ProductDto> productDtos = List.of(dtos);
        when(productService.getAllProducts()).thenReturn(products);
        when(productService.getconvertedproductlistdto(products)).thenReturn(productDtos);

        ResponseEntity<ProductResponse> pro=productController.getAllProducts();
        assertNotNull(pro);
        assertEquals(HttpStatus.OK,pro.getStatusCode());
        assertNotNull(pro.getBody());
    }
    @Test
    public void getProductByIdTest(){
        Category dummyCategory = new Category();
        dummyCategory.setName("Dummy Category");
        dummyCategory.setId(1);
        dummyCategory.setProducts(null);
        Product products = new Product(1, "Dummy Product", "Dummy Brand", 100.0, 50, "This is a dummy product for testing.", dummyCategory);
        ProductDto dtos=new ProductDto();
        dtos.setId(1);
        dtos.setName("Dummy Product");
        dtos.setBrand("Dummy Brand");
        dtos.setPrice(100.0);
        dtos.setInventory(50);
        dtos.setCategory(dummyCategory);
        dtos.setImages(null);
        ProductDto productDtos = dtos;
        when(productService.findProductById(1)).thenReturn(products);
        when(productService.converttoDto(products)).thenReturn(productDtos);
        ResponseEntity<ProductResponse> pro=productController.getProductById(1);
        assertNotNull(pro);
        assertEquals(HttpStatus.OK,pro.getStatusCode());
        assertNotNull(pro.getBody());
    }



}
