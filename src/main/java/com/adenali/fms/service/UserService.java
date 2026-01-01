package com.adenali.fms.service;

import com.adenali.fms.model.AppUser;
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


    public void saveUser(AppUser appUser) throws UsernameNotFoundException {
        log.info("Saving user: {}", appUser);
       userRepository.save(appUser);
       log.info("User saved successfully");
    }

    public AppUser findUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }
}
