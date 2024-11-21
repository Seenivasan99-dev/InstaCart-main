package com.mycart.mycart.Security.User;

import com.mycart.mycart.Entities.User;
import com.mycart.mycart.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= Optional.ofNullable(userRepo.findByEmail(email)).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return ShopUserDetails.builduser(user);
    }
}
