package com.examportal.controllers;

import com.examportal.helper.UserFoundException;
import com.examportal.models.Role;
import com.examportal.models.User;
import com.examportal.models.UserRole;
import com.examportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test")
    public String test(){
        return "exam server backend";
    }

    //create user url
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {

//        Set to store userRoles
        Set<UserRole> roles = new HashSet<>();

//        encoding password with BCrypt password encoder
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

//        create a role for normal user.
//        request coming from this url is by default a normal user's request.
        Role role = new Role();
        role.setRoleName("NORMAL");
        role.setRoleId(45L);

//        create userRole with a role and an user
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

//        add the userRole to a Set.
        roles.add(userRole);


//        creates user through UserService interface which has a concrete class
//        UserServiceImplementation
        return this.userService.createUser(user, roles);
    }

//    path variables are in curly braces they can be given dynamic values
//    get user by username
//    localhost:8282/user/asdf
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
        return this.userService.getUser(username);
    }

//    delete user by id
//    localhost:8282/user/23
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
         this.userService.deleteUser(id);
    }

//    delete user by username
//    localhost:8282/user/?username=qweir
    @DeleteMapping("/")
    public String deleteUserByUsername(@RequestParam(value = "username") String username){
        this.userService.deleteUserByUsername(username);
        return (username+" Deleted");
    }





//    update the user
    //    this method needs rechecking as it is not replacing the old record but it is creating a whole new record
//    also it does not update the roles of the record.
    @PutMapping("/")
    public User updateUser(@RequestBody User user) throws Exception {


        Set<UserRole> roles = new HashSet<>();

//        encoding password with BCrypt password encoder
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        Role role = new Role();
        role.setRoleName("NORMAL");
        role.setRoleId(45L);

//        create userRole with a role and an user
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

//        add the userRole to a Set.
        roles.add(userRole);
        return this.userService.updateUser(user, roles);

    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserFoundException ex){
        return (ResponseEntity<?>) ResponseEntity.notFound();
    }
}
