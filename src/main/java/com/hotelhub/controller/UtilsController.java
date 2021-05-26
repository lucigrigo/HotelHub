package com.hotelhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
public class UtilsController {
    @GetMapping("/utils/get/hotels")
    public ResponseEntity<Object> getHotels(
            @RequestParam(name = "location") String location) {
        return null;
    }

    @GetMapping("/utils/get/hotel/rooms")
    public ResponseEntity<Object> getRooms(
            @RequestParam(name = "hotel_id") String hotel_id) {
        return null;
    }

    @GetMapping("/utils/get/hotel/bookings/confirmed")
    public ResponseEntity<Object> getHotelConfirmedBookings(
            @RequestParam(name = "hotel_id") String hotel_id) {
        return null;
    }

    @GetMapping("/utils/get/hotel/bookings/to_delete")
    public ResponseEntity<Object> getToDeleteBookings(
            @RequestParam(name = "hotel_id") String hotel_id) {
        return null;
    }

    @GetMapping("/utils/get/user/bookings/confirmed")
    public ResponseEntity<Object> getUserConfirmedBookings(
            @RequestParam(name = "hotel_id") String user_id) {
        return null;
    }

    @GetMapping("/utils/get/user/bookings/pending")
    public ResponseEntity<Object> getUserPendingBookings(
            @RequestParam(name = "hotel_id") String user_id) {
        return null;
    }

    @GetMapping("/utils/get/rooms/available")
    public ResponseEntity<Object> getAvailableRooms(
            @RequestParam(name = "hotel_id") String hotel_id,
            @RequestParam(name = "start_date") Date start_date,
            @RequestParam(name = "end_date") Date end_date
    ) {
        return null;
    }
}
