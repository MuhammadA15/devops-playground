package com.example.demo.services.Impl;

import com.example.demo.services.UserService;
import com.example.demo.controllers.dto.UserPatchRequest;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public String getUserByUserName(String username) {
        return repository.findByUsername(username).toString();
    }

    @Override
    public void createUser(User user) {
        repository.save(user);
    }

    @Override
    public String getUserByFirstName(String firstname) {
        // System.err.println(firstname);
        return repository.findByFirstName(firstname).toString();
    }

    @Override
    public String getUserByLastName(String lastname) {
        // System.err.println(lastname);
        return repository.findByLastName(lastname).toString();
    }

    @Override
    public String patchUser(UserPatchRequest patchRequest) {
        if (patchRequest.getUserId() == null) {
            return "Error: No UserId provided";
        }

        User user = repository.findByUserId(patchRequest.getUserId());
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
    public String deleteUser(String id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return "User succesfully deleted";
        } else {
            throw new RuntimeException("User not found");
        }
    }

}