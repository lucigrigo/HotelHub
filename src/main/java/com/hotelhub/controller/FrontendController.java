package com.hotelhub.controller;

import com.hotelhub.model.Booking;
import com.hotelhub.model.Hotel;
import com.hotelhub.model.Room;
import com.hotelhub.services.AdminServices;
import com.hotelhub.services.ClientServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class FrontendController {
    @GetMapping("/client/location/hotels")
    public ResponseEntity<Object> getLocationHotels(
            @RequestParam(name = "location") String location) {
        return ClientServices.getLocationHotels(location);
    }

    @GetMapping("/client/hotel/rooms")
    public ResponseEntity<Object> getHotelRooms(
            @RequestParam(name = "hotel_id") String hotel_id,
            @RequestParam(name = "no_of_people") int no_of_people,
            @RequestParam(name = "type") String type) {
        return ClientServices.getHotelRooms(hotel_id, no_of_people, type);
    }

    @PostMapping("/client/booking/create")
    public ResponseEntity<Object> createBooking(
            @RequestBody Booking booking
    ) {
        return ClientServices.createBooking(booking);
    }

    @GetMapping("/client/booking/cancel")
    public ResponseEntity<Object> cancelBooking(
            @RequestParam(name = "booking_id") String booking_id
    ) {
        return ClientServices.cancelBooking(booking_id);
    }

    @PostMapping("/client/facilities")
    public ResponseEntity<Object> accessFacilities() {
        return ClientServices.accessFacilities();
    }

    @GetMapping("/admin/booking/approve")
    public ResponseEntity<Object> approveBooking(
            @RequestParam(name = "booking_id") String booking_id) {
        return AdminServices.approveBooking(booking_id);
    }

    @GetMapping("/admin/booking/cancel")
    public ResponseEntity<Object> approveCancel(
            @RequestParam(name = "booking_id") String booking_id) {
        return AdminServices.cancelBooking(booking_id);
    }

    @PostMapping("/admin/actions/hotel/add")
    public ResponseEntity<Object> addHotel(
            @RequestBody Hotel hotel,
            @RequestParam(name = "user_id") String user_id) {
        return AdminServices.addHotel(hotel, user_id);
    }

    @GetMapping("/admin/actions/hotel/delete")
    public ResponseEntity<Object> deleteHotel(
            @RequestParam(name = "user_id") String user_id,
            @RequestParam(name = "hotel_id") String hotel_id
    ) {
        return AdminServices.deleteHotel(user_id, hotel_id);
    }

    @PostMapping("/admin/actions/room/add")
    public ResponseEntity<Object> addRoom(
            @RequestBody Room room) {
        return AdminServices.addRoom(room);
    }

    @GetMapping("/admin/actions/room/delete")
    public ResponseEntity<Object> deleteRoom(
            @RequestParam(name = "room_id") String room_id) {
        return AdminServices.deleteRoom(room_id);
    }

    @PostMapping("/admin/actions/alter_facility")
    public ResponseEntity<Object> alterFacility() {
        return AdminServices.alterFacility();
    }
}
