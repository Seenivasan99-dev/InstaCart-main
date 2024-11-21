package com.mycart.mycart.ControllerTests;

import com.mycart.mycart.Entities.Category;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Exceptions.NoAvailableProducts;
import com.mycart.mycart.Exceptions.ProductAlreadyExsist;
import com.mycart.mycart.Exceptions.ProductNotFound;
import com.mycart.mycart.Repository.CategoryRepo;
import com.mycart.mycart.Repository.ImageRepo;
import com.mycart.mycart.Repository.ProductRepo;
import com.mycart.mycart.Request.AddProductRequest;
import com.mycart.mycart.Request.UpdateProductRequest;
import com.mycart.mycart.Service.ProductService;
import org.apache.tomcat.jni.Library;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mycart.mycart.Service.productServiceIImpl;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    productServiceIImpl productService;

    @Mock
    ProductRepo productRepo;


    @Mock
    CategoryRepo categoryRepo;

    @Mock
    ImageRepo imageRepo;

    @Test
    public void findProductbyIdTest(){
        Product dummyproduct = new Product();
        Category category = new Category();
        dummyproduct.setId(1);
        dummyproduct.setName("Dummyproduct");
        dummyproduct.setDescription("Dummyproduct");
        category.setId(1);
        category.setName("Category");
        dummyproduct.setCategory(category);
        dummyproduct.setPrice(100);
        dummyproduct.setImages(null);
        dummyproduct.setBrand("dunmmy");
        dummyproduct.setInventory(70);
        when(productRepo.findByid(1)).thenReturn(dummyproduct);
        Product res=productService.findProductById(1);
        assertEquals(1,res.getId());
    }
    @Test
    public void findProductByIdExceptionTest(){
        int id=0;
        when(productRepo.findByid(0)).thenReturn(null);
        ProductNotFound pronf=assertThrows(ProductNotFound.class,()->{
            productService.findProductById(id);
        });
        assertEquals("Product not found with Id"+id,pronf.getMessage());
    }
    @Test
    public void getAllProductsTest(){
        Product dummyproduct = new Product();
        Category category = new Category();
        dummyproduct.setId(1);
        dummyproduct.setName("Dummyproduct");
        dummyproduct.setDescription("Dummyproduct");
        dummyproduct.setCategory(category);
        category.setId(1);
        category.setName("Category");
        dummyproduct.setPrice(100);
        dummyproduct.setImages(null);
        dummyproduct.setBrand("dunmmy");
        dummyproduct.setInventory(70);
        Product dummyproduct2 = new Product();
        dummyproduct2.setId(2);
        dummyproduct2.setName("Dummyproduct2");
        dummyproduct2.setDescription("Dummyproduct2");
        category.setId(1);
        category.setName("Category");
        dummyproduct2.setCategory(category);
        dummyproduct2.setPrice(1000);
        dummyproduct2.setImages(null);
        dummyproduct2.setBrand("dunmmy2");
        dummyproduct2.setInventory(700);
        List<Product> pro=new ArrayList();
        pro.add(dummyproduct);
        pro.add(dummyproduct2);
        category.setProducts(pro);



        when(productRepo.findAll()).thenReturn(pro);

        List<Product> res=productService.getAllProducts();

        assertEquals(pro.size(),res.size());
        assertEquals(pro.get(0).getId(),res.get(0).getId());
        assertEquals(pro.get(0).getName(),res.get(0).getName());
        assertEquals(pro.get(0).getDescription(),res.get(0).getDescription());
        assertEquals(pro.get(0).getCategory().getId(),res.get(0).getCategory().getId());
        assertEquals(pro.get(0).getPrice(),res.get(0).getPrice());
        assertEquals(pro.get(0).getImages(),res.get(0).getImages());
        assertEquals(pro.get(0).getBrand(),res.get(0).getBrand());
        assertEquals(pro.get(0).getInventory(),res.get(0).getInventory());
        assertEquals(pro.get(1).getName(),res.get(1).getName());
        assertEquals(pro.get(1).getDescription(),res.get(1).getDescription());
        assertEquals(pro.get(1).getCategory().getId(),res.get(1).getCategory().getId());
        assertEquals(pro.get(1).getPrice(),res.get(1).getPrice());
        assertEquals(pro.get(1).getImages(),res.get(1).getImages());
        assertEquals(pro.get(1).getBrand(),res.get(1).getBrand());
        assertEquals(pro.get(1).getInventory(),res.get(1).getInventory());
    }

    @Test
    public void getAllProductsTestException(){
        when(productRepo.findAll()).thenReturn(null);
        NoAvailableProducts res=assertThrows(NoAvailableProducts.class,()->{
            if(productService.getAllProducts()==null){
                throw new NoAvailableProducts("No Products Available");
            }
        });
        assertEquals("No Products Available",res.getMessage());
    }
    @Test
  public  void productAlreadyexsiscaseforAddmethod(){
    Product product=new Product();
    product.setId(1);
    product.setName("Dummyproduct");
    product.setDescription("Dummyproduct");
    Category category=new Category();
    category.setId(1);
    category.setName("Category");
    product.setCategory(category);
    product.setPrice(100);
    product.setImages(null);
    product.setBrand("dunmmy");
    product.setInventory(70);
    lenient().when(productRepo.existsByNameAndBrand("Dummyproduct","dunmmy")).thenReturn(true);
        AddProductRequest add=new AddProductRequest();
        add.setName("Dummyproduct");
        add.setDescription("Dummyproduct");
        add.setCategory(category);
        add.setPrice(100);
        add.setBrand("dunmmy");
    ProductAlreadyExsist ex=assertThrows(ProductAlreadyExsist.class,()->{
        productService.addProduct(add);
    });
    assertEquals("Already Exisist",ex.getMessage());
  }





}
