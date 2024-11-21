package com.mycart.mycart.Repository;

import com.mycart.mycart.Entities.CartIteam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartIteamRepo extends JpaRepository<CartIteam, Integer> {
    public void deleteAllById(long id);

    public void deleteById(long id);

}
