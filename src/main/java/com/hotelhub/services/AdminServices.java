package com.hotelhub.services;

import com.google.cloud.firestore.Firestore;
import com.hotelhub.controller.DatabaseController;
import com.hotelhub.model.Hotel;
import com.hotelhub.model.Room;
import com.hotelhub.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AdminServices {

    public static ResponseEntity<Object> approveBooking(String booking_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.hasBooking(database, booking_id)) {
                DatabaseController.approveBooking(database, booking_id);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else
                return new ResponseEntity<>(false, null, HttpStatus.OK);
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> cancelBooking(String booking_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.hasApprovedBooking(database, booking_id)) {
                DatabaseController.adminCancelBooking(database, booking_id);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else
                return new ResponseEntity<>(false, null, HttpStatus.OK);
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

    public static ResponseEntity<Object> deleteHotel(String user_id, String hotel_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.hasHotel(database, hotel_id)) {
                DatabaseController.deleteHotel(database, user_id, hotel_id);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> addRoom(Room room) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.checkRoomInHotel(database, room))
                return new ResponseEntity<>(false, null, HttpStatus.OK);
            else {
                DatabaseController.addRoom(database, room);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            }
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> deleteRoom(String room_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.hasRoom(database, room_id)) {
                DatabaseController.deleteRoom(database, room_id);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else
                return new ResponseEntity<>(false, null, HttpStatus.OK);
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> alterFacility() {
        return null;
    }

    public static ResponseEntity<Object> getHotelById(String hotel_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.hasHotel(database, hotel_id)) {
                Hotel hotel = DatabaseController.getHotelById(database, hotel_id);
                return new ResponseEntity<>(hotel, null, HttpStatus.OK);
            } else
                return new ResponseEntity<>(null, null, HttpStatus.OK);
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> deleteFacility(String facility_id) {
        try {
            Firestore database = DatabaseController.getDatabase();
            if (DatabaseController.hasFacility(database, facility_id)) {
                DatabaseController.deleteFacility(database, facility_id);
                return new ResponseEntity<>(true, null, HttpStatus.OK);
            } else
                return new ResponseEntity<>(false, null, HttpStatus.OK);
        } catch (IOException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
