package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/")
	public String home() {
		return "Welcome to the home route";
	}

	@GetMapping("/users")
	public String getUsers() {
		return "RllyBigD";
	}

}