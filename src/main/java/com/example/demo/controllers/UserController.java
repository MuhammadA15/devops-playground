package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserPatchRequest;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String home() {
		return "Welcome to the home route v2";
	}

	@GetMapping("/user")
	public List<User> getUser(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "firstname", required = false) String firstname,
			@RequestParam(value = "lastname", required = false) String lastname) {

		return userService.getUser(username, firstname, lastname);
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

	@PatchMapping("/user/{userId}")
	public String patchUser(@RequestBody UserPatchRequest patchRequest, @PathVariable String userId) {
		return userService.patchUser(userId, patchRequest);
	}

	@DeleteMapping("/user/{userId}")
	public String deleteUser(@PathVariable String userId) {
		try {
			userService.deleteUser(userId);
			return "User succesfully deleted";
		} catch (Exception e) {
			return "Operation failed " + e.getMessage();
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody UserLoginDto userCredentialData) {
		System.out.println("someone tried to login!");
		return userService.userLogin(userCredentialData);
	}

}