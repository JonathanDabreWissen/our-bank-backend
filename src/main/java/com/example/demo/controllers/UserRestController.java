package com.example.demo.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.service.UserService;

@RestController
public class UserRestController {
	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public Iterable<User> getUsers(){
		return service.getUsers();
	}
	
	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable int id){
		return service.getUserById(id);
	}
	
	@PostMapping("/users")
	public String insertUsers(@RequestBody User u) {
		return service.insertUser(u);
	}
	
	@PostMapping("/login")
	public Map<String, String> loginUser(@RequestBody Map<String, String> credentials) {
		String email = credentials.get("email");
		String password = credentials.get("password");
		return service.loginUser(email, password);
	}
	
	@PostMapping("/deposit")
	public Map<String, String> deposit(@RequestHeader("Authorization") String token, 
	                                   @RequestBody Map<String, Integer> request) {
		int amount = request.get("amount");
		return service.deposit(token.replace("Bearer ", ""), amount);
	}

	
	@PostMapping("/withdraw")
	public Map<String, String> withdraw(@RequestHeader("Authorization") String token, 
	                                    @RequestBody Map<String, Integer> request) {
		int amount = request.get("amount");
		return service.withdraw(token.replace("Bearer ", ""), amount);
	}
}

