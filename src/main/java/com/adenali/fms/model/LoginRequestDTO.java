package com.adenali.fms.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {


    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username is too large")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 20, message = "Password must be at least 8 characters")
    private String password;

}
