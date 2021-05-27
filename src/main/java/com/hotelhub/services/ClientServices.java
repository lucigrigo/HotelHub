package com.hotelhub.services;

import com.google.cloud.firestore.Firestore;
import com.hotelhub.controller.DatabaseController;
import com.hotelhub.model.Booking;
import com.hotelhub.model.Hotel;
import com.hotelhub.model.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientServices {

    public static ResponseEntity<Object> createBooking(Booking booking) {
        try {
            Firestore database = DatabaseController.getDatabase();
            Booking newBooking = DatabaseController.createBooking(database, booking);
            if (newBooking != null) {
                return new ResponseEntity<>(booking, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> cancelBooking(String booking_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.searchBooking(database, booking_id)) {
                DatabaseController.clientCancelBooking(database, booking_id);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> accessFacilities() {
        return null;
    }

    public static ResponseEntity<Object> getLocationHotels(String location) {
        try {
            Firestore database = DatabaseController.getDatabase();
            List<Hotel> hotels = DatabaseController.getLocationHotels(database, location);
            if (!hotels.isEmpty()) {
                return new ResponseEntity<>(hotels, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> getHotelRooms(String hotel_id, int no_of_people, String type) {
        try {
            Firestore database = DatabaseController.getDatabase();
            List<Room> rooms = DatabaseController.getHotelRooms(database, hotel_id, no_of_people, type);
            if (!rooms.isEmpty()) {
                return new ResponseEntity<>(rooms, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
