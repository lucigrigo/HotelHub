package com.hotelhub.controller;

import com.hotelhub.model.User;
import com.hotelhub.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @PostMapping("/users/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        return UserServices.createUser(user);
    }

    @GetMapping("/users/login")
    public ResponseEntity<Object> loginUser(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) {
        return UserServices.loginUser(email, password);
    }

    @GetMapping("/users/get")
    public ResponseEntity<Object> getUserById(
            @RequestParam(name = "user_id") String user_id) {
        return UserServices.getUserById(user_id);
    }

    @GetMapping("/users/actions/first_room")
    public ResponseEntity<Object> firstRoom(
            @RequestParam(name = "hotel_id") String hotel_id) {
        return UserServices.firstRoom(hotel_id);
    }

    @GetMapping("/users/actions/rooms")
    public ResponseEntity<Object> allRooms(
            @RequestParam(name = "hotel_id") String hotel_id) {
        return UserServices.getAllRooms(hotel_id);
    }

    @GetMapping("/users/actions/hotels")
    public ResponseEntity<Object> allHotels() {
        return UserServices.getAllHotels();
    }

    @GetMapping("/users/actions/facilities")
    public ResponseEntity<Object> allFacilities() {
        return UserServices.getAllFacilities();
    }

    @GetMapping("/users/actions/hotel_facilities")
    public ResponseEntity<Object> getFacilitiesByHotel(
            @RequestParam(name = "hotel_id") String hotel_id) {
        return UserServices.getFacilitiesByHotel(hotel_id);
    }
}
