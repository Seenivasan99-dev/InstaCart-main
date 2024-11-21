package com.mycart.mycart.Repository;

import com.mycart.mycart.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    public Product findByid(int id);


    @Query("select p from Product p where p.category.id IN(select q.id from Category q where q.name=:category)")
    public List<Product> findByCategory( @Param("category") String category);

    @Query("select x from Product x where x.brand=:brand")
    public List<Product> findProductByBrand(@Param("brand") String brand);

    @Query("select y from Product y where y.brand =:brand and y.category.id IN(select n.id from Category n where n.name=:category)")
    public List<Product> findByCategoryAndBrand(@Param("category") String category, @Param("brand") String brand);

    @Query("select o from Product o where o.name=:name")
    public List<Product> findByName(@Param("name") String name);

    @Query("select t from Product t where t.name=:name and t.brand=:brand")
    public List<Product> findByBrandAndName( @Param("brand") String brand,@Param("name") String Name);

    @Query("select count(*) from Product p where p.brand=:brand and p.name=:name")
    public long countProductByBrandAndName(@Param("brand") String brand, @Param("name") String Name);

    public boolean existsByNameAndBrand(String name,String brand);

    }
