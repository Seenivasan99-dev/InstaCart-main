package com.mycart.mycart.Repository;

import com.mycart.mycart.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);

    public User findByEmail(String email);




}
