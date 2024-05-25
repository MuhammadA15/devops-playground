package com.example.demo.services.Impl;

import com.example.demo.services.UserService;
import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserPatchRequest;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getUser(String username, String firstname, String lastname) {
        List<User> user = new ArrayList<User>();
        if (username != null) {
            user = repository.findByUsername(username);
        } else if (firstname != null) {
            user = repository.findByFirstName(firstname);
        } else if (lastname != null) {
            user = repository.findByLastName(lastname);
        } else {
            user = repository.findAll();
        }

        return user;
    }

    @Override
    public void createUser(User user) {
        if (user != null) {
            repository.save(user);
        } else {
            throw new IllegalArgumentException("User entity cannot be null");
        }
    }

    @Override
    public String patchUser(String userId, UserPatchRequest patchRequest) {
        if (userId == null) {
            return "Error: No UserId provided";
        }

        User user = repository.findByUserId(userId);
        if (user == null) {
            return "Error: Could not find user to update";
        }

        UserPatchRequest patchRequestClass = new UserPatchRequest();
        Field[] fields = patchRequestClass.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Object value = f.get(patchRequest);
                if (value != null) {
                    try {
                        Field userField = User.class.getDeclaredField(f.getName());
                        userField.setAccessible(true);
                        userField.set(user, value);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println(user);
        repository.save(user);

        return "Successfully Updated User!";
    }

    @Override
    public ResponseEntity<?> userLogin(UserLoginDto userCredentialData) {
        UserResponseDto userResponse = new UserResponseDto();
        if (userCredentialData.getEmail() == null) {
            userResponse.setMessage("Email is required");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userResponse);
        }
        if (userCredentialData.getPassword() == null) {
            userResponse.setMessage("Password is required");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userResponse);
        }

        List<User> users = repository.findByEmail(userCredentialData.getEmail());

        if (users.isEmpty()) {
            userResponse.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userResponse);
        }

        if (!users.get(0).getPassword().equals(userCredentialData.getPassword())) {
            userResponse.setMessage("Email or Password is incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userResponse);
        }

        userResponse.setMessage("Success!");
        userResponse.setUsers(users);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Override
    public String deleteUser(String userId) {
        if (repository.existsById(userId)) {
            repository.deleteById(userId);
            return "User succesfully deleted";
        } else {
            throw new RuntimeException("User not found");
        }
    }

}