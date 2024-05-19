package com.example.demo.dto;

import java.util.List;
import java.util.Optional;
import com.example.demo.models.User;

public class UserResponseDto {

    private List<User> users;
    private String message;

    public UserResponseDto() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}