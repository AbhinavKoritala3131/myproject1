package org.example.controller;

import org.example.entity.Order;
import org.example.entity.User;
import org.example.entity.UserOrderDTO;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}/orders")
    public UserOrderDTO getUserOrders(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User user = userOptional.get();
        List<Order> orders= Arrays.asList(restTemplate.getForObject("http://localhost:8081/orders/user/"+id,Order[].class));
        UserOrderDTO userOrderDTO = new UserOrderDTO();
        userOrderDTO.setUser(user);
        userOrderDTO.setOrders(orders);
        return userOrderDTO;
    }
    @GetMapping("/getAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid  @RequestBody User user) {
        if (user.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Do not send ID while creating User");
        }
        return ResponseEntity.status(201).body(userService.createUser(user));


    }
    //getbyid
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //delete by id

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
            boolean deleted = userService.deleteUserById(id);
            if (deleted) {
                return ResponseEntity.ok("User deleted succesfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id" + id);

            }
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

}
