package com.mycart.mycart.Service;

import com.mycart.mycart.Dto.ProductDto;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Exceptions.ProductAlreadyExsist;
import com.mycart.mycart.Request.AddProductRequest;
import com.mycart.mycart.Request.UpdateProductRequest;

import java.util.List;

public interface ProductService {

    public Product addProduct(AddProductRequest addProductRequest) throws ProductAlreadyExsist;
    public Product findProductById(int id);
    public void deleteProductById(int id);
    public Product UpdateProduct(UpdateProductRequest product, int id);
    public List<Product> getAllProducts();
    public List<Product> getProductByCategory(String category);
    public List<Product> getProductByBrand(String brand);
    public List<Product> getProductByCategoryAndBrand(String category, String brand);
    public List<Product> getProductByName(String name);
    public List<Product> getProductByBrandAndName(String brand, String Name);
    public long countProductByBrandAndName(String brand, String Name);

    List<ProductDto> getconvertedproductlistdto(List<Product> productList);

    ProductDto converttoDto(Product product);
}
