package com.mycart.mycart.Repository;

import com.mycart.mycart.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    public List<Order> findByUserId(long userId);
}
