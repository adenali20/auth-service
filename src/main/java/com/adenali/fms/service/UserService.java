package com.adenali.fms.service;

import com.adenali.fms.model.User;
import com.adenali.fms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {


    @Autowired

    private UserRepository userRepository;
    public void saveUser(User user) throws UsernameNotFoundException {
        log.info("Saving user: {}", user);
        userRepository.save(user);
        log.info("User saved successfully");
    }

    public User findUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }
}
