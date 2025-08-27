package org.example.service;


import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
                    u.setName(updatedUser.getName());
                    u.setEmail(updatedUser.getEmail());
                    User savedUser = userRepository.save(u);
                    return ResponseEntity.ok(savedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
