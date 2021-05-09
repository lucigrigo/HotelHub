package com.hotelhub.controller;

import com.hotelhub.model.User;
import com.hotelhub.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrontendController {
    @PostMapping("/users/create")
    public ResponseEntity<Object> createUser(
            @RequestBody User user
    ) {
        return UserServices.createUser(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<Object> loginUser(
            @RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "password") String password
    ) {
        return UserServices.loginUser(email, password);
    }
}
