package org.example.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.entity.User;
import org.example.exception.UserNotFound;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    public ResponseEntity<User> update(Long id, User updatedUser) {

        return userRepository.findById(id).map(u -> {
            if (updatedUser.getName()!=null){
                    u.setName(updatedUser.getName());}
            if (updatedUser.getEmail()!=null){
                    u.setEmail(updatedUser.getEmail());}

            User savedUser = userRepository.save(u);
            return ResponseEntity.status(HttpStatus.OK).body(savedUser);
                })

                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
    }
    public ResponseEntity<String> del(Long id) {
        boolean check = userRepository.existsById(id);
        if (check) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User with ID " + id + " deleted Successfully");
        } else {

            throw new UserNotFound("User with ID " + id + " not found");

        }


    }
}
