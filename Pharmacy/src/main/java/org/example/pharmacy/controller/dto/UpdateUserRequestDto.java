package org.example.pharmacy.controller.dto;

import jakarta.validation.constraints.Size;

public class UpdateUserRequestDto {

    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    private String username;

    @Size(min = 6, message = "password must be at least 6 characters long")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
