package com.example.demo.services;

import java.util.List;

import com.example.demo.models.User;

public interface UserService {

    List<User> getAllUsers();
    
    String getUserByUserName(String username);

    void createUser(User user);

}