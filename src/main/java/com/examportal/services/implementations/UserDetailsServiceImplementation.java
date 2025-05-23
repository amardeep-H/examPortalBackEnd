package com.examportal.services.implementations;

import com.examportal.models.User;
import com.examportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = this.userRepository.findByUsername(username);
        if(user==null){
            System.out.println("User Not Found By this Username.");
            throw new UsernameNotFoundException("!!! No User Found !!!");
        }
        return user;
    }
}
