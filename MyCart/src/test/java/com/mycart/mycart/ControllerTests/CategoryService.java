package com.mycart.mycart.ControllerTests;

import com.mycart.mycart.Entities.Category;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Exceptions.CategoryNotFound;
import com.mycart.mycart.Repository.CategoryRepo;
import com.mycart.mycart.Service.CategoryServiceIImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryService {

    @InjectMocks
    CategoryServiceIImpl categoryservice;

    @Mock
    CategoryRepo categoryRepo;

    @Test
    public void addcategoryExceptionCase(){
        String name="Category";
        Category category = new Category();
        category.setName(name);
        lenient().when(categoryRepo.existsByName(name)).thenReturn(true);
        CategoryNotFound cats=assertThrows(CategoryNotFound.class,()->{
            categoryservice.addCategory(category);
        });
        assertEquals("Category Not Found",cats.getMessage());
    }
    @Test
    public void addCategorywithoutException(){
        Category category = new Category();
        category.setName("Category");
        category.setId(1);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);
        Category newcat=categoryservice.addCategory(category);
        assertEquals("Category",newcat.getName());
    }

    @Test
    public void updateCategory(){
        Category category = new Category();
        category.setName("Category");
        category.setId(1);
        Category updatedcat=new Category();
        updatedcat.setName("UpdatedCategory");
        updatedcat.setId(1);
        doReturn(updatedcat).when(categoryRepo).getReferenceById(1);
        when(categoryRepo.save(any(Category.class))).thenAnswer(invocation -> {
            Category cat=invocation.getArgument(0);
            cat.setName("UpdatedCategory");
            return cat;
        });
        Category result=categoryservice.updateCategory(updatedcat,1);
        assertEquals("UpdatedCategory",result.getName());
    }

    @Test
    public void updatedCategoryException(){
        Category category = new Category();
        doReturn(null).when(categoryRepo).getReferenceById(1);
        CategoryNotFound res=assertThrows(CategoryNotFound.class,()->{
            try {
                categoryservice.updateCategory(category, 1);
            }
            catch(NullPointerException e){
                throw new CategoryNotFound("Category Not Found");
            }
        });
        assertEquals("Category Not Found",res.getMessage());
    }

    @Test
    public void deletecategory(){
        Category category = new Category();
        category.setName("Category");
        category.setId(1);
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        categoryservice.deleteCategory((int) category.getId());
        verify(categoryRepo).delete(category);
    }
    @Test
    public void deleteCategoryException(){
        Category category = new Category();
        category.setName("Category");
        category.setId(1);
        when(categoryRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFound.class,()->{
            categoryservice.deleteCategory((int) category.getId());
        });
    }

    @Test
    public void getallcategory(){
        Category c1=new Category();
        c1.setId(1);
        c1.setName("Electronics");
        Category c2=new Category();
        c2.setId(2);
        c2.setName("Sports");
        Category c3=new Category();
        c3.setId(3);
        c3.setName("Fashion");
        List<Category> catlist=new ArrayList<>();
        catlist.add(c1);
        catlist.add(c2);
        catlist.add(c3);
        doReturn(catlist).when(categoryRepo).findAll();
        List<Category> result=categoryRepo.findAll();
        assertNotNull(catlist);
        assertEquals(3,result.size());
       assertEquals("Electronics",result.get(0).getName());
       assertEquals("Sports",result.get(1).getName());
    }

    @Test
    public void getAllCategoryException(){
        doReturn(null).when(categoryRepo).findAll();
        CategoryNotFound exceresult=assertThrows(CategoryNotFound.class,()->{
            categoryservice.getAllCategories();
        });
        assertEquals("Category Not Found",exceresult.getMessage());
    }
    @Test
    public void getCategoeybyname(){
        Category category=new Category();
        category.setName("Category");
        category.setId(1);
        lenient().when(categoryRepo.findByName("Category")).thenReturn(category);
        Category result=categoryservice.findCategoryByName("Category");
        assertEquals("Category",result.getName());
    }
    @Test
    public void getCtaegorybynameexception(){
        Category category=new Category();
        category.setName("Category");
        category.setId(1);
        doReturn(null).when(categoryRepo).findByName("Category");
        CategoryNotFound exceresult=assertThrows(CategoryNotFound.class,()->{
            categoryservice.findCategoryByName("Category");
        });
        assertEquals("Category Not Found",exceresult.getMessage());
    }


}
