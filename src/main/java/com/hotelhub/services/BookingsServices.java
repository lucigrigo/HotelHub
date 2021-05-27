package com.hotelhub.services;

import org.springframework.http.ResponseEntity;

import java.util.Date;

public class BookingsServices {
    public static ResponseEntity<Object> getHotelConfirmedBookings(String hotel_id) {
        return null;
    }

    public static ResponseEntity<Object> getHotelToDeleteBookings(String hotel_id) {
        return null;
    }

    public static ResponseEntity<Object> getUserConfirmedBookings(String user_id) {
        return null;
    }

    public static ResponseEntity<Object> getUserPendingBookings(String user_id) {
        return null;
    }

    public static ResponseEntity<Object> getAvailableRooms(String hotel_id, Date start_date, Date end_date) {
        return null;
    }
}
