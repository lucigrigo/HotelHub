package com.hotelhub.controller;

import com.hotelhub.model.Hotel;
import com.hotelhub.model.Room;
import com.hotelhub.services.AdminServices;
import com.hotelhub.services.ClientServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class FrontendController {

    @PostMapping("/client/booking/create")
    public ResponseEntity<Object> createBooking() {
        return ClientServices.createBooking();
    }

    @PostMapping("/client/booking/cancel")
    public ResponseEntity<Object> cancelBooking() {
        return ClientServices.cancelBooking();
    }

    @PostMapping("/client/facilities")
    public ResponseEntity<Object> accessFacilities() {
        return ClientServices.accessFacilities();
    }

    @PostMapping("/admin/approve/booking")
    public ResponseEntity<Object> approveBooking() {
        return AdminServices.approveBooking();
    }

    @PostMapping("/admin/approve/cancel")
    public ResponseEntity<Object> approveCancel() {
        return AdminServices.approveCancel();
    }

    @PostMapping("/admin/actions/hotel/add")
    public ResponseEntity<Object> addHotel(
            @RequestBody Hotel hotel,
            @RequestParam(name = "user_id") String user_id) {
        return AdminServices.addHotel(hotel, user_id);
    }

    @PostMapping("/admin/actions/hotel/delete")
    public ResponseEntity<Object> deleteHotel(
            @RequestParam(name = "user_id") String user_id,
            @RequestParam(name = "hotel_id") String hotel_id) {
        return AdminServices.deleteHotel(user_id, hotel_id);
    }

    @PostMapping("/admin/actions/room/add")
    public ResponseEntity<Object> addRoom(
            @RequestBody Room room) {
        return AdminServices.addRoom(room);
    }

    @PostMapping("/admin/actions/room/delete")
    public ResponseEntity<Object> deleteRoom(
            @RequestParam(name = "room_id") String room_id) {
        return AdminServices.deleteRoom(room_id);
    }

    @PostMapping("/admin/actions/alter_facility")
    public ResponseEntity<Object> alterFacility() {
        return AdminServices.alterFacility();
    }
}
