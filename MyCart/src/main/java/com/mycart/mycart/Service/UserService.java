package com.mycart.mycart.Service;

import com.mycart.mycart.Dto.CartDto;
import com.mycart.mycart.Dto.ProductDto;
import com.mycart.mycart.Dto.UserDto;
import com.mycart.mycart.Entities.Cart;
import com.mycart.mycart.Entities.Product;
import com.mycart.mycart.Entities.Role;
import com.mycart.mycart.Entities.User;
import com.mycart.mycart.Exceptions.UserNotFounById;
import com.mycart.mycart.Repository.RoleRepository;
import com.mycart.mycart.Repository.UserRepo;
import com.mycart.mycart.Request.CreateUserRequest;
import com.mycart.mycart.Request.UpdateUserRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserServiceImpl{

    @Autowired
    UserRepo userepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordencoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User getuserbyId(long userid) {
        return userepo.findById(userid).orElseThrow(()->{
            new UserNotFounById("user not found by id"+userid);
            return null;
        });
    }

    @Override
    @Transactional
    public User createuser(CreateUserRequest createuserrequest) {
        return Optional.of(createuserrequest).filter(usr->!userepo.existsByEmail(createuserrequest.getEmail())).map(req->{
            User user = new User();
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                Role defrole=roleRepository.findByName("USER");
                user.getRoles().add(defrole);
            }
            user.setEmail(createuserrequest.getEmail());
            user.setPassword(passwordencoder.encode(createuserrequest.getPassword()));
            user.setFirstName(createuserrequest.getFirstName());
            user.setLastName(createuserrequest.getLastName());
            return userepo.save(user);
        }).orElseThrow(()->{
            new UserNotFounById("user not found by id"+createuserrequest.getEmail());
            return null;
        });
    }

    @Override
    public User Updateuser(UpdateUserRequest updateuserrequest, long userid) {
        return Optional.of(userepo.findById(userid).map(user->{
            user.setFirstName(updateuserrequest.getFirstName());
            user.setLastName(updateuserrequest.getLastName());
            return userepo.save(user);
        })).get().orElseThrow(()->{ new UserNotFounById("user not found by id"+userid);
            return null;
        });
    }

    @Override
    public void deleteuser(long userid) {

        userepo.deleteById(userid);
    }

    public UserDto convertUserToUserDto(User user) {
        modelMapper.typeMap(User.class,UserDto.class).addMappings(mp->{
            mp.map(User::getCart,UserDto::setCartdto);
            mp.map(User::getOrders,UserDto::setOrderdto);

        });
        modelMapper.typeMap(Cart.class, CartDto.class).addMappings(mp->{
            mp.map(Cart::getCartiteam,CartDto::setCartiteamdto);
        });
        modelMapper.typeMap(Product.class, ProductDto.class).addMappings(mp->{
            mp.map(Product::getId,ProductDto::setId);
        });
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenicatedUser() {
        Authentication authenication= SecurityContextHolder.getContext().getAuthentication();
        String email =authenication.getName();
        return userepo.findByEmail(email);
    }
}
