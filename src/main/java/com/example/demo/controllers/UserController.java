package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.User;
import com.example.demo.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String home() {
		return "Welcome to the home route";
	}

	@GetMapping("/user")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/user/{username}")
	public String getUsers(@PathVariable String username) {
		return userService.getUserByUserName(username);
	}

	@PostMapping("/user")
	public String createUser(@RequestBody User user) {
		try {
			userService.createUser(user);
		} catch (Exception e) {
			return "Operation failed";
		}

		return "Success!";
	}

}