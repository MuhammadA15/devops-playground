package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.UserPatchRequest;
import com.example.demo.models.User;

public interface UserService {
    
    List<User> getUser(String username, String firstname, String lastname);

    void createUser(User user);

    String patchUser(String userId, UserPatchRequest patchRequest);

    String deleteUser(String userId);

}