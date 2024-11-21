package com.mycart.mycart.Service;

import com.mycart.mycart.Entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(int id);
    @Query("select u from Category  u where u.name=:name")
    Category findCategoryByName(@Param("name") String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,int id);
    void deleteCategory(int id);
}
