package com.hotelhub.services;

import com.google.cloud.firestore.Firestore;
import com.hotelhub.controller.DatabaseController;
import com.hotelhub.model.Hotel;
import com.hotelhub.model.Room;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AdminServices {

    public static ResponseEntity<Object> approveBooking() {
        return null;
    }

    public static ResponseEntity<Object> approveCancel() {
        return null;
    }

    public static ResponseEntity<Object> addHotel(Hotel hotel, String user_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (!DatabaseController.searchHotel(database, hotel)) {
                DatabaseController.addHotel(database, hotel, user_id);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> deleteHotel(String hotel_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if(DatabaseController.hasHotel(database, hotel_id)) {
                DatabaseController.deleteHotel(database, hotel_id);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> alterFacility() {
        return null;
    }

    public static ResponseEntity<Object> deleteRoom(String room_id) {
        return null;
    }

    public static ResponseEntity<Object> addRoom(Room room) {
        return null;
    }
}
