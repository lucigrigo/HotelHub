package com.hotelhub.controller;

import com.hotelhub.services.BookingsServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
public class BookingsController {
    @GetMapping("/bookings/hotel/confirmed")
    public ResponseEntity<Object> getHotelConfirmedBookings(
            @RequestParam(name = "hotel_id") String hotel_id) {
        return BookingsServices.getHotelConfirmedBookings(hotel_id);
    }

    @GetMapping("/bookings/hotel/to_delete")
    public ResponseEntity<Object> getHotelToDeleteBookings(
            @RequestParam(name = "hotel_id") String hotel_id) {
        return BookingsServices.getHotelToDeleteBookings(hotel_id);
    }

    @GetMapping("/bookings/user/confirmed")
    public ResponseEntity<Object> getUserConfirmedBookings(
            @RequestParam(name = "hotel_id") String user_id) {
        return BookingsServices.getUserConfirmedBookings(user_id);
    }

    @GetMapping("/bookings/user/pending")
    public ResponseEntity<Object> getUserPendingBookings(
            @RequestParam(name = "hotel_id") String user_id) {
        return BookingsServices.getUserPendingBookings(user_id);
    }

    @GetMapping("/bookings/rooms/available")
    public ResponseEntity<Object> getAvailableRooms(
            @RequestParam(name = "hotel_id") String hotel_id,
            @RequestParam(name = "start_date") Date start_date,
            @RequestParam(name = "end_date") Date end_date
    ) {
        return BookingsServices.getAvailableRooms(hotel_id, start_date, end_date);
    }
}
