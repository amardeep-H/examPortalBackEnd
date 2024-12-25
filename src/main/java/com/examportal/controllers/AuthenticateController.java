package com.examportal.controllers;

import com.examportal.Configuration.JwtUtils;
import com.examportal.models.JwtRequest;
import com.examportal.models.JwtResponse;
import com.examportal.models.User;
import com.examportal.services.implementations.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

//    generate token
    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken (@RequestBody JwtRequest jwtRequest) throws Exception {
        try{

            this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("Username not found.");
        }

//        user is authenticated
        UserDetails userDetails = this.userDetailsServiceImplementation.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }




    private void authenticate(String username, String password) throws Exception {
        try{

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        }catch(DisabledException ee){
            throw new Exception("User is Disabled."+ ee.getMessage());

        }catch (BadCredentialsException ee){
            throw new Exception("Invalid Credentials."+ee.getMessage());
        }catch (Exception e){
            System.out.println("authenticate method problem."+e.getMessage());
        }
    }

//  returns current user
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return (User) this.userDetailsServiceImplementation.loadUserByUsername(principal.getName());
    }

}
