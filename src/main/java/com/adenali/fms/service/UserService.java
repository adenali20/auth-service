package com.adenali.fms.service;

import com.adenali.fms.exceptions.EmailAlreadyExistsException;
import com.adenali.fms.model.*;
import com.adenali.fms.repository.UserActivationTokenRepository;
import com.adenali.fms.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
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
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserActivationTokenRepository activationTokenRepository;

    public RegisterResponse saveUser(RegisterRequest request) {

        log.info("Registering user: {}", request);

        if (findUserByEmail(request.getEmail()) != null) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        // 1️⃣ Create & save user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(false); // disabled until activation

        user = userRepository.save(user);

        // 2️⃣ Create activation token
        String token = UUID.randomUUID().toString();

        UserActivationToken activationToken = new UserActivationToken();
        activationToken.setUser(user);
        activationToken.setToken(token);
        activationToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        activationToken.setUsed(false);

        activationTokenRepository.save(activationToken);

        // TODO:3️⃣ Send activation email

        log.info("User registered successfully, activation email sent to {}", user.getEmail());

        return new RegisterResponse(
                "Registration successful. Please check your email to activate your account.",
                null
        );
    }


    public User findUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }
}
