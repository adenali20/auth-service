package com.adenali.authservice.service;

import com.adenali.authservice.model.User;
import com.adenali.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public void saveUser(User user) throws UsernameNotFoundException {
       userRepository.save(user);
    }

    public User findUserByUserName(String userName) throws UsernameNotFoundException {
        return userRepository.findByUsername(userName);
    }
}
