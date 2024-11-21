package com.mycart.mycart.Repository;

import com.mycart.mycart.Entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ImageRepo extends JpaRepository<Images, Integer> {

    public List<Images> findByProductId(int productid);

}
