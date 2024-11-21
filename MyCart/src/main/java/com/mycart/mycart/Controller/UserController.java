package com.mycart.mycart.Controller;

import com.mycart.mycart.Dto.UserDto;
import com.mycart.mycart.Entities.User;
import com.mycart.mycart.Request.CreateUserRequest;
import com.mycart.mycart.Request.UpdateUserRequest;
import com.mycart.mycart.Request.UserRequestResponse;
import com.mycart.mycart.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userservice;

    @GetMapping("/users/{userid}")
    public ResponseEntity<UserRequestResponse> getUserbyId(@PathVariable  long userid) {
        User user=userservice.getuserbyId(userid);
        UserDto userdto=userservice.convertUserToUserDto(user);
        return ResponseEntity.ok(new UserRequestResponse("fetched Sucessfully",userdto));

    }
    @PostMapping("/users/createuser")
    public ResponseEntity<UserRequestResponse> createuser(@RequestBody CreateUserRequest createUserRequest) {
        User user=userservice.createuser(createUserRequest);
        UserDto userdto=userservice.convertUserToUserDto(user);
        return ResponseEntity.ok(new UserRequestResponse("created Sucessfully",userdto));
    }
    @PutMapping("/users/updateuser/{userid}")
    public ResponseEntity<UserRequestResponse> updateuser(@RequestBody UpdateUserRequest updateUserRequest,@PathVariable long userid) {
        User user=userservice.Updateuser(updateUserRequest,userid);
        return ResponseEntity.ok(new UserRequestResponse("updated Sucessfully",user));
    }
    @DeleteMapping("/users/deleteuser/{userid}")
    public ResponseEntity<UserRequestResponse> deleteuser(@PathVariable long userid) {
        userservice.deleteuser(userid);
        return ResponseEntity.ok(new UserRequestResponse("deleted Sucessfully",null));
    }
}
