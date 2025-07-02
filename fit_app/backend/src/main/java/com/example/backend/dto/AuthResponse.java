package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("token")
    private String token;

    // Constructor for successful responses with token
    public AuthResponse(Long userId, String email, String name, boolean success, String message, String token) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.success = success;
        this.message = message;
        this.token = token;
    }

    // Constructor for error responses (without token)
    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.token = null;
    }

    // Explicit getter methods to ensure proper serialization
    public boolean isSuccess() {
        return success;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
