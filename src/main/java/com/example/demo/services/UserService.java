package com.example.demo.services;

import java.util.List;

import com.example.demo.controllers.dto.UserPatchRequest;
import com.example.demo.models.User;

public interface UserService {

    List<User> getAllUsers();
    
    String getUserByUserName(String username);

    void createUser(User user);

    String getUserByFirstName(String firstname);

    String getUserByLastName(String lastname);

    String patchUser(UserPatchRequest patchRequest);

}