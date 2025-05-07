package org.example.pharmacy.controller.dto;

import org.example.pharmacy.commonTypes.UserRole;

public class RegisterResponseDto {
    private long userId;
    private String username;
    private UserRole role;

    public RegisterResponseDto( String username, UserRole role, long userId) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setUserRole(UserRole Role) {
        this.role = role;
    }
}