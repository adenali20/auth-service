package com.adenali.fms.controller;

import com.adenali.fms.exceptions.EmailAlreadyExistsException;
import com.adenali.fms.model.*;
import com.adenali.fms.service.ActivationService;
import com.adenali.fms.service.JwtService;
import com.adenali.fms.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authservice")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final ActivationService activationService;

    @PostMapping("/user/signup")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        log.info("creating user with email:{}",request.getEmail());
        if(userService.findUserByEmail(request.getEmail()) != null){
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        RegisterResponse registerResponse=userService.saveUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerResponse);
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponseDTO> apiLogin (@Valid @RequestBody LoginRequestDTO loginRequest)  {
        log.info("user {} tried to login",loginRequest.getUsername());
       return jwtService.apiLogin(loginRequest);
    }

    @GetMapping("/user/get")
    public String get(){
        log.info("get user<<:>>");
      return "successfull from deply-2";
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(
            @RequestParam("token") String token
    ) {
        activationService.activateUser(token);
        return ResponseEntity.ok("Account activated successfully");
    }
}
