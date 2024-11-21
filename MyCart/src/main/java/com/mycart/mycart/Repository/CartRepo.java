package com.mycart.mycart.Repository;

import com.mycart.mycart.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepo extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c where c.id = :cartId")
    public Cart findById( @Param("cartId") long cartId);
    public void deleteById(long cartId);
    public Cart findCartByUserId(long userId);
}
