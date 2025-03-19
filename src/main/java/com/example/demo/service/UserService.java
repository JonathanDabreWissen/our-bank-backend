package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.JwtUtil;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public Iterable<User> getUsers(){
		return repo.findAll();
	}
	
	public Optional<User> getUserById(int id){
		return repo.findById(id);
	}
	
	public ResponseEntity<Map<String, String>> insertUser(User u) {
	    Map<String, String> response = new HashMap<>();
	    
	    if (repo.existsByEmail(u.getEmail())) {
	        response.put("message", "The user already exists. Choose a different email id");
	        return ResponseEntity.status(409).body(response);
	    }

	    repo.save(u);
	    response.put("message", "Successfully registered the User");
	    return ResponseEntity.status(200).body(response);
	}

	public Map<String, String> loginUser(String email, String password) {
		User user = repo.findByEmailAndPassword(email, password);
		Map<String, String> response = new HashMap<>();

		if (user != null) {
			String token = JwtUtil.generateToken(email);
			response.put("message", "Login Successful");
			response.put("token", token);
		} else {
			response.put("message", "Invalid Email or Password");
		}
		return response;
	}
	
	public Map<String, String> deposit(String token, int amount){
		Map<String, String> response = new HashMap<>();
		String email = JwtUtil.extractEmail(token);
		
		if(email == null) {
			response.put("message", "Invalid or Expired Token");
			return response;
		}
		
		User user = repo.findByEmail(email);
		if(user == null) {
			response.put("message", "User not found");
			return response;
		}
		
		int newBalance = user.getBalance() + amount;
		repo.updateBalance(email, newBalance);
		
		response.put("message", "Deposit Successful");
		response.put("new_balance", String.valueOf(newBalance));
		return response;
	}
	
	public Map<String, String> withdraw(String token, int amount) {
		Map<String, String> response = new HashMap<>();
		String email = JwtUtil.extractEmail(token);

		if (email == null) {
			response.put("message", "Invalid or Expired Token");
			return response;
		}

		User user = repo.findByEmail(email);
		if (user == null) {
			response.put("message", "User not found");
			return response;
		}

		if (user.getBalance() < amount) {
			response.put("message", "Insufficient Balance");
			return response;
		}

		int newBalance = user.getBalance() - amount;
		repo.updateBalance(email, newBalance);

		response.put("message", "Withdrawal Successful");
		response.put("new_balance", String.valueOf(newBalance));
		return response;
	}
	
}
