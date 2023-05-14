package com.reactspring.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reactspring.exceptions.ResourceNotFoundException;
import com.reactspring.models.User;
import com.reactspring.repositories.UserRepository;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // @GetMapping("/")
    // public String index() {

    // return "index";
    // }

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.userRepository.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found !"));
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("user/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable(value = "id") Long id) {
        // Why not directly update it ?
        return this.userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setSurname(newUser.getSurname());
                    user.setEmail(newUser.getEmail());
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    return this.userRepository.save(user);
                })
                // Or else get ? what does this mean ?
                .orElseGet(() -> {
                    newUser.setId(id);
                    return this.userRepository.save(newUser);
                });
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable(value = "id") Long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found !"));
        this.userRepository.delete(user);
        // What is this build for ?
        return ResponseEntity.ok().build();
    }

}
