package com.hotelhub.services;

import com.hotelhub.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserServices {
    public static ResponseEntity<Object> createUser(User newUser) {
        // TODO: add user to database; check if he already exists
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static ResponseEntity<Object> loginUser(String email, String password) {
        // TODO: check if user exists in database
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
