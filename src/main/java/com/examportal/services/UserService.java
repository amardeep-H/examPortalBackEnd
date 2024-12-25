package com.examportal.services;

import com.examportal.helper.UserFoundException;
import com.examportal.helper.UserNotFoundException;
import com.examportal.models.User;
import com.examportal.models.UserRole;

import java.util.Set;

public interface UserService {

//    create user
    public User createUser(User user, Set<UserRole> userRole) throws Exception;

    //     get user by username
    public User getUser(String username);

//    delete user by id
    public void deleteUser(Long userId);

//    delete user by username
    public String deleteUserByUsername(String username);

//    update userby username
//    //    this method needs rechecking as it is not replacing the old record but it is creating a whole new record
////    also it does not update the roles of the record.
    public User updateUser(User user, Set<UserRole> userRole)throws UserFoundException;
}
