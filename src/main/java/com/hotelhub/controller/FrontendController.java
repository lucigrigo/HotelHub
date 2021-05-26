package com.hotelhub.controller;

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
    public ResponseEntity<Object> addHotel() {
        return AdminServices.addHotel();
    }

    @PostMapping("/admin/actions/hotel/delete")
    public ResponseEntity<Object> deleteHotel() {
        return AdminServices.deleteHotel();
    }

    @PostMapping("/admin/actions/room/add")
    public ResponseEntity<Object> addRoom() {
        return AdminServices.addRoom();
    }

    @PostMapping("/admin/actions/room/delete")
    public ResponseEntity<Object> deleteRoom() {
        return AdminServices.deleteRoom();
    }

    @PostMapping("/admin/actions/alter_facility")
    public ResponseEntity<Object> alterFacility() {
        return AdminServices.alterFacility();
    }
}
