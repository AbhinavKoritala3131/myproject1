package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "Updated Code Pushed";
    }
    @GetMapping("/loc")
    public String myloc() {
        return "New API location : USA";
    }
}
