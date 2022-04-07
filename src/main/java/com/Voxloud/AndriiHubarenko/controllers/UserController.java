package com.Voxloud.AndriiHubarenko.controllers;

import com.Voxloud.AndriiHubarenko.domain.CredentialsDetails;
import com.Voxloud.AndriiHubarenko.domain.User;
import com.Voxloud.AndriiHubarenko.services.UserService;
import com.Voxloud.AndriiHubarenko.services.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserServiceInterface service;

    @Autowired
    public UserController (UserService service) {
        this.service = service;
    }

    @PostMapping(path = "/credentials")
    public ResponseEntity<User> authenticate(@RequestBody CredentialsDetails details) {
        User response = service.authenticate(details.getEmail(), details.getPassword());
        response.setPassword("");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User response = service.createUser(user);
        response.setPassword("");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<User> getUser (@PathVariable long id) {
        User response = service.getUser(id);
        response.setPassword("");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/credentials/{email}/{password}")
    public ResponseEntity<User> getUser(@PathVariable String email, @PathVariable String password) {
        User response = service.authenticate(email, password);
        response.setPassword("");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        User response = service.updateUser(id, user);
        response.setPassword("");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        String response = service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
