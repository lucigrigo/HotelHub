package com.hotelhub.services;

import com.google.cloud.firestore.Firestore;
import com.hotelhub.controller.DatabaseController;
import com.hotelhub.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class UserServices {
    public static ResponseEntity<Object> createUser(User newUser) {
        // TODO: add user to database; check if he already exists
        try {
            Firestore database = DatabaseController.getDatabase();
            if (!DatabaseController.searchUser(database, newUser.getEmail())) {
                DatabaseController.addUser(database, newUser);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> loginUser(String email, String password) {
        // TODO: check if user exists in database
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.checkCredentials(database, email, password)) {
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
