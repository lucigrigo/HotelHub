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
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.checkCredentials(database, email, password)) {
                User user = DatabaseController.getUser(database, email, password);
                return new ResponseEntity<>(user, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> getAllRooms(String hotel_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.hasHotel(database, hotel_id))
                return new ResponseEntity<>(DatabaseController.getAllRooms(database, hotel_id), null, HttpStatus.OK);
            else
                return new ResponseEntity<>(null, null, HttpStatus.OK);
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> getAllHotels() {
        try {
            Firestore database = DatabaseController.getDatabase();
            return new ResponseEntity<>(DatabaseController.getAllHotels(database), null, HttpStatus.OK);
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> firstRoom(String hotel_id) {
        // TODO
        return null;
    }

    public static ResponseEntity<Object> getAllFacilities() {
        // TODO
        return null;
    }

    public static ResponseEntity<Object> getFacilitiesByHotel(String hotel_id) {
        // TODO
        return null;
    }

    public static ResponseEntity<Object> deleteFacility(String facility_id) {
        // TODO
        return null;
    }
}
