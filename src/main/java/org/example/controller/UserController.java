package org.example.controller;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Do not send ID while creating User");
        }
        return ResponseEntity.status(201).body(userService.createUser(user));


    }

}
