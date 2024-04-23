package com.example.demo.services.Impl;

import com.example.demo.services.UserService;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
        System.err.println(firstname);
        return repository.findByFirstName(firstname).toString();
    }

    @Override
    public String getUserByLastName(String lastname) {
        System.err.println(lastname);
        return repository.findByLastName(lastname).toString();
    }

}