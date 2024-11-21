package com.mycart.mycart.Controller;

import com.mycart.mycart.Dto.ProductDto;
import com.mycart.mycart.Entities.Category;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Exceptions.ProductAlreadyExsist;
import com.mycart.mycart.Exceptions.ProductNotFound;
import com.mycart.mycart.Repository.CategoryRepo;
import com.mycart.mycart.Repository.ProductRepo;
import com.mycart.mycart.Request.AddProductRequest;
import com.mycart.mycart.Request.UpdateProductRequest;
import com.mycart.mycart.Response.ProductResponse;
import com.mycart.mycart.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Products")
public class ProductController {

    @Autowired
    ProductService proservice;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ProductRepo productRepo;


    @GetMapping("/AllProducts")
    public ResponseEntity<ProductResponse> getAllProducts(){
        List<Product> allProducts=proservice.getAllProducts();
        List<ProductDto> allproductsdto=proservice.getconvertedproductlistdto(allProducts);
        return ResponseEntity.ok(new ProductResponse("Product List", allproductsdto));
    }

    @GetMapping("/Product/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int productId){
        Product product=proservice.findProductById(productId);
        ProductDto productdto=proservice.converttoDto(product);
        try {
            if (product != null) {
                return ResponseEntity.ok(new ProductResponse("Product Found Sucessfully", productdto));
            }
            else{
                throw new ProductNotFound("Product Not Found");
            }
        }
        catch(ProductNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductResponse("Product Not Found", e.getMessage()));
        }

    }

    @PostMapping("/addProduct")
    public ResponseEntity<ProductResponse>  addProduct(@RequestBody AddProductRequest addProductRequest){
        try {
            Product addpro = Optional.of(proservice.addProduct(addProductRequest)).get();
            return ResponseEntity.ok(new ProductResponse("Product Added Sucessfully", addpro));
        }
        catch(ProductAlreadyExsist e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ProductResponse("Product Alresy Exsist Try to add new One", e.getMessage()));
        }
    }

    @PutMapping("/UpdateProduct/{ProdId}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody UpdateProductRequest updateProductRequest,@PathVariable int ProdId){
        try {
            Product updatepro = Optional.ofNullable(proservice.UpdateProduct(updateProductRequest, ProdId)).orElseThrow(() -> {
                throw new ProductNotFound("ProductNot Found");
            });
            return ResponseEntity.ok(new ProductResponse("Product Updated Sucessfully", updatepro));
        }
        catch(ProductNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductResponse("Product Not Found", e.getMessage()));
        }
    }

    @DeleteMapping("/deleteProduct/{proid}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable int proid){
        try{

            Product delpro=proservice.findProductById(proid);

            proservice.deleteProductById(proid);
            if(delpro!=null){
                return ResponseEntity.ok(new ProductResponse("Product Deleted Sucessfully", delpro));
            }
            else{
                throw new ProductNotFound("ProductNot Found");
            }
        }
        catch(ProductNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductResponse("Product Not Found", e.getMessage()));
        }
    }






}
