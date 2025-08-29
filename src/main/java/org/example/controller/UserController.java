package org.example.controller;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

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
//    GetById , DeleteById

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid  @RequestBody User user) {
        if (user.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Do not send ID while creating User");
        }
        return ResponseEntity.status(201).body(userService.createUser(user));


    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,  @RequestBody User updatedUser) {
//        if (updatedUser.getId() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is Required to update this user");
//        }
        if (updatedUser.getName() == null && updatedUser.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No data provided to update.");
        }
        if ( updatedUser.getId()!=null && id!=updatedUser.getId()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is not matching");
        }
        return userService.update(id,updatedUser);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> RemoveUser(@PathVariable Long id) {
        if ( id == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID Invalid");

        } else {


            return userService.del(id);


        }
    }
}
