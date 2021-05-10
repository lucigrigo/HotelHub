package com.hotelhub.services;

import com.google.cloud.firestore.Firestore;
import com.hotelhub.controller.DatabaseController;
import com.hotelhub.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class UserServices {
    private static int no_users = 0;
    public static ResponseEntity<Object> createUser(User newUser) throws IOException, ExecutionException, InterruptedException {
        // TODO: add user to database; check if he already exists
        Firestore database = DatabaseController.getDatabase();
        if (!DatabaseController.searchUser(database, newUser.getEmail())) {
            no_users++;
            DatabaseController.addUser(database, newUser);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static ResponseEntity<Object> loginUser(String email, String password) throws IOException, ExecutionException, InterruptedException {
        // TODO: check if user exists in database
        Firestore database = DatabaseController.getDatabase();
        if (DatabaseController.checkCredentials(database, email, password)) {

        } else {

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
