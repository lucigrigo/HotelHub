package com.hotelhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrontendController {
    @PostMapping("/users/create")
    public ResponseEntity<Object> createUser() {
        return null;
    }

    @PostMapping("/users/login")
    public ResponseEntity<Object> loginUser() {
        return null;
    }
}
