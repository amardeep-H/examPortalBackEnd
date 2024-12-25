package com.examportal.services.implementations;

import com.examportal.helper.UserFoundException;
import com.examportal.helper.UserNotFoundException;
import com.examportal.models.User;
import com.examportal.models.UserRole;
import com.examportal.repository.RoleRepository;
import com.examportal.repository.UserRepository;
import com.examportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


//create user
    @Override
    public User createUser(User user, Set<UserRole> userRole) throws Exception {

//        check if user is already present or not
        User local = this.userRepository.findByUsername(user.getUsername());

//        if users present already...
        if(local!=null){
            System.out.println("!!! User is there already !!!");
            throw new UserFoundException();
        }else{
//            if user not present already create user

//            add all the roles in the Set userRole to database
            for (UserRole ur:userRole){
                roleRepository.save(ur.getRole());
            }

//          set userRole to the user and save user in the database.
            user.getUserRoles().addAll(userRole);
            local = this.userRepository.save(user);

        }
//      return the user saved in database
        return local;
    }

//    getting userby username
    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

//    delete userby id
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }


    //    delete user by username
    @Override
    public String deleteUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        Long userId = user.getId();
        this.deleteUser(userId);
        return username+" Deleted";
    }


//    this method needs rechecking as it is not replacing the old record but it is creating a whole new record
//    also it does not update the roles of the record.
    @Override
    public User updateUser(User user, Set<UserRole> userRole) throws UserFoundException {

//            add all the roles in the Set userRole to database
            for (UserRole ur:userRole){
                roleRepository.save(ur.getRole());
            }

//          set userRole to the user and save user in the database.
            user.getUserRoles().addAll(userRole);
            return this.userRepository.save(user);

    }


}
