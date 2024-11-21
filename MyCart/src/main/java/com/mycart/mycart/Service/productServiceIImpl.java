package com.mycart.mycart.Service;

import com.mycart.mycart.Dto.ProductDto;
import com.mycart.mycart.Entities.Category;
import com.mycart.mycart.Entities.Images;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Exceptions.NoAvailableProducts;
import com.mycart.mycart.Exceptions.ProductAlreadyExsist;
import com.mycart.mycart.Exceptions.ProductNotFound;
import com.mycart.mycart.Repository.CategoryRepo;
import com.mycart.mycart.Repository.ImageRepo;
import com.mycart.mycart.Repository.ProductRepo;
import com.mycart.mycart.Request.AddProductRequest;
import com.mycart.mycart.Request.ImageDto;
import com.mycart.mycart.Request.UpdateProductRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class productServiceIImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryRepo catRepo;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ImageRepo imageRepo;

    @Override
    public Product addProduct(AddProductRequest addProductRequest) throws ProductAlreadyExsist {

        if(ProductExsist(addProductRequest.getName(),addProductRequest.getBrand())){
            throw new ProductAlreadyExsist("Already Exisist");
        }
        Category category = Optional.ofNullable(catRepo.findByName(addProductRequest.getCategory().getName())).orElseGet(
                ()->{
                    Category newCategory = new Category();
                    newCategory.setName(addProductRequest.getCategory().getName());
                    return  catRepo.save(newCategory);
                });
        addProductRequest.setCategory(category);
        return productRepo.save(CreateProduct(addProductRequest,category));

    }


    private Product CreateProduct(AddProductRequest addProductRequest, Category category) {
        return new Product(
                        addProductRequest.getId(),
                        addProductRequest.getName(),
                        addProductRequest.getBrand(),
                        addProductRequest.getPrice(),
                        addProductRequest.getInventory(),
                addProductRequest.getDescription(),
                        category
                );
    }

    private boolean ProductExsist(String name,String brand){
        return productRepo.existsByNameAndBrand(name, brand);
    }


    @Override
    public Product findProductById(int id) {

        return Optional.ofNullable(productRepo.findByid(id)).orElseThrow(()->{
            throw new ProductNotFound("Product not found with Id"+id);}
        );
    }

    @Override
    public void deleteProductById(int id) {
        productRepo.deleteById(id);
    }


    private Product UpdateExsistingProduct(Product exsistingproduct, UpdateProductRequest updateProductRequest) {
        exsistingproduct.setName(updateProductRequest.getName());
        exsistingproduct.setBrand(updateProductRequest.getBrand());
        exsistingproduct.setPrice(updateProductRequest.getPrice());
        exsistingproduct.setInventory(updateProductRequest.getInventory());
        exsistingproduct.setDescription(updateProductRequest.getDescription());
        Category cat=catRepo.findByName(exsistingproduct.getCategory().getName());
        exsistingproduct.setCategory(cat);
        return exsistingproduct;
    }

    @Override
    public Product UpdateProduct(UpdateProductRequest product, int id) {
        Product exsisitedproduct=Optional.ofNullable(productRepo.findByid(id)).orElseThrow(()->{
            throw new ProductNotFound("Product not found with Id"+id);
        });
        Product  newproduct=UpdateExsistingProduct(exsisitedproduct,product);
        return productRepo.save(newproduct);
    }

    @Override
    public List<Product> getAllProducts() {
        return Optional.ofNullable(productRepo.findAll()).orElseThrow(()->{
            throw new NoAvailableProducts("No Products Available");
        });
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return Optional.of(productRepo.findByCategory(category)).orElseThrow(()->{
            throw new ProductNotFound("No Products Available");
        });
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepo.findProductByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String Name) {
        return productRepo.findByBrandAndName(brand, Name);
    }

    @Override
    public long countProductByBrandAndName(String brand, String Name) {
        return productRepo.countProductByBrandAndName(brand, Name);
    }

    @Override
    public List<ProductDto> getconvertedproductlistdto(List<Product> productList) {
        return productList.stream().map(this::converttoDto).toList();
    }

    @Override
    public ProductDto converttoDto(Product product) {
        ProductDto productDto=modelMapper.map(product, ProductDto.class);
        List<Images> images=  imageRepo.findByProductId(product.getId());
        List<ImageDto> imageDtos= images.stream().map(image->modelMapper.map(image,ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
