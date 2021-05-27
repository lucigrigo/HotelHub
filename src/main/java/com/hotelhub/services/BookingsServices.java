package com.hotelhub.services;

import com.google.cloud.firestore.Firestore;
import com.hotelhub.controller.DatabaseController;
import com.hotelhub.model.Booking;
import com.hotelhub.model.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookingsServices {

    public static ResponseEntity<Object> getHotelConfirmedBookings(String hotel_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            List<Booking> bookings = DatabaseController.getHotelConfirmedBookings(database, hotel_id);
            if (!bookings.isEmpty()) {
                return new ResponseEntity<>(bookings, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> getHotelToDeleteBookings(String hotel_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            List<Booking> bookings = DatabaseController.getHotelToDeleteBookings(database, hotel_id);
            if (!bookings.isEmpty()) {
                return new ResponseEntity<>(bookings, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> getUserConfirmedBookings(String user_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            List<Booking> bookings = DatabaseController.getUserConfirmedBookings(database, user_id);
            if (!bookings.isEmpty()) {
                return new ResponseEntity<>(bookings, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> getUserPendingBookings(String user_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            List<Booking> bookings = DatabaseController.getUserPendingBookings(database, user_id);
            if (!bookings.isEmpty()) {
                return new ResponseEntity<>(bookings, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> getRoomById(String room_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.hasRoom(database, room_id)) {
                Room room = DatabaseController.getRoomById(database, room_id);
                return new ResponseEntity<>(room, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> getAvailableRooms(String hotel_id, Date start_date, Date end_date) {
        try {
            Firestore database = DatabaseController.getDatabase();
            List<Room> rooms = DatabaseController.getAvailableRooms(database, hotel_id, start_date, end_date);
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
