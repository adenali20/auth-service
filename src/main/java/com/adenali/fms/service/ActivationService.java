package com.adenali.fms.service;

import com.adenali.fms.exceptions.EmailAlreadyExistsException;
import com.adenali.fms.model.RegisterRequest;
import com.adenali.fms.model.RegisterResponse;
import com.adenali.fms.model.User;
import com.adenali.fms.model.UserActivationToken;
import com.adenali.fms.repository.UserActivationTokenRepository;
import com.adenali.fms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class ActivationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserActivationTokenRepository activationTokenRepository;



    @Transactional
    public void activateUser(String token) {

        UserActivationToken activationToken =
                activationTokenRepository.findByTokenAndUsedFalse(token)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (activationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        User user = activationToken.getUser();
        user.setEnabled(true);

        activationToken.setUsed(true);

        userRepository.save(user);
        activationTokenRepository.save(activationToken);
    }




}
