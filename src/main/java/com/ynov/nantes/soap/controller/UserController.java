package com.ynov.nantes.soap.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.nantes.soap.entity.User;
import com.ynov.nantes.soap.repository.UserRepository;

@RestController
public class UserController {
    
    private UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/users")
    List<User> getUsers() {
      return this.userRepository.findAll();
    }
    
    
    @GetMapping("/user/{email}")
    User getUserByEmail(@PathVariable String email) {
      return this.userRepository.findUserByEmail(email);
    }
    
    @PostMapping("/user")
    User newUser(@RequestBody User user) {
      return this.userRepository.save(user);
    }
    @PutMapping("/user")
    User editUser(@RequestBody User user) {
      return this.userRepository.save(user);
    }
    
    @DeleteMapping("/user/{id}")
    void rmUserById(@PathVariable int id) {
        this.userRepository.deleteById(id);
    }
}
