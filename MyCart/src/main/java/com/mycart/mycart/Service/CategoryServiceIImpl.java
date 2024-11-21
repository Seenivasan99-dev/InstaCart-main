package com.mycart.mycart.Service;

import com.mycart.mycart.Entities.Category;
import com.mycart.mycart.Exceptions.CategoryNotFound;
import com.mycart.mycart.Repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceIImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(int id) {
        return  Optional.of(categoryRepo.getReferenceById(id)).orElseThrow(()->{
            throw new CategoryNotFound("Category Not Found");
        });
    }

    @Override
    public Category findCategoryByName(String name) {
        return Optional.ofNullable(categoryRepo.findByName(name)).orElseThrow(()->{
            throw new CategoryNotFound("Category Not Found");
        });
    }

    @Override
    public List<Category> getAllCategories() {
        return Optional.ofNullable(categoryRepo.findAll())
                .orElseThrow(()->{
            throw new CategoryNotFound("Category Not Found");
        });
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepo.existsByName(category.getName())).map(categoryRepo::save).orElseThrow(()->{
            throw new CategoryNotFound("Category Not Found");
        });
    }

    @Override
    public Category updateCategory(Category category, int id) {
        return Optional.of(getCategoryById(id)).map(oldcat->{oldcat.setName(category.getName());
        return categoryRepo.save(oldcat);}

        ).orElseThrow(()->{
            return new CategoryNotFound("Category not found");
        });
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete,()->{
            throw new CategoryNotFound("Category not found");
        });

    }
}
