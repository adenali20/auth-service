package com.adenali.fms.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterResponse {
    private String message;
    private String token;

    public RegisterResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    // getters
}
