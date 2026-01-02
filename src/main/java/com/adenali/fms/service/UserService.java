package com.adenali.fms.service;

import com.adenali.fms.exceptions.EmailAlreadyExistsException;
import com.adenali.fms.model.*;
import com.adenali.fms.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public RegisterResponse saveUser(RegisterRequest request) throws UsernameNotFoundException {

        log.info("Registering user: {}", request);

        if(findUserByEmail(request.getEmail()) != null){
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(false); // force disabled

        userRepository.save(user);

        log.info("User saved successfully");

        return new RegisterResponse(
                "Registration successful. Await admin approval",
                null
        );
    }


    public User findUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }
}
