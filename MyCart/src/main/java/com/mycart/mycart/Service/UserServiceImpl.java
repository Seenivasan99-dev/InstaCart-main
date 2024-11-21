package com.mycart.mycart.Service;

import com.mycart.mycart.Dto.UserDto;
import com.mycart.mycart.Entities.User;
import com.mycart.mycart.Request.CreateUserRequest;
import com.mycart.mycart.Request.UpdateUserRequest;

public interface UserServiceImpl {
    User getuserbyId(long userid);
    User createuser(CreateUserRequest createuserrequest);
    User Updateuser(UpdateUserRequest updateuserrequest, long userid);
    void deleteuser(long userid);
    UserDto convertUserToUserDto(User user);

    User getAuthenicatedUser();
}
