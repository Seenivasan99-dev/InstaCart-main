package com.mycart.mycart.Repository;

import com.mycart.mycart.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    @Query("select c from Category c where c.name=:name")
    public Category findByName(String name);

    boolean existsByName(String name);
}
