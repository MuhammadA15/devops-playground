package com.example.demo.dto;

import java.util.Optional;

public class UserLoginDto {

    private string email;
    private string password;

    public UserLoginDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.email = password;
    }
}