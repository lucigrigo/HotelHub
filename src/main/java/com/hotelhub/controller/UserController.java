package com.hotelhub.controller;

import com.hotelhub.model.User;
import com.hotelhub.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {
    @PostMapping("/users/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        return UserServices.createUser(user);
    }

    @GetMapping("/users/login")
    public ResponseEntity<Object> loginUser(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) {
        return UserServices.loginUser(email, password);
    }
}
