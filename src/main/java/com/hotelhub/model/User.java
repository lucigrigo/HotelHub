package com.hotelhub.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Entity
public class User {
    private final int DEFAULT_ID = 0;

    @Id
    private int user_id;

    private String name;

    private String email;

    private String password;

    private boolean is_admin;

    private int hotel_admin;

//    public User(String name, String email, String password, boolean is_admin, int hotel_admin) {
//        new User(DEFAULT_ID, name, email, password, is_admin, hotel_admin);
//    }

    public User(int id, String name, String email, String password, boolean is_admin, int hotel_admin) {
        this.user_id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.is_admin = is_admin;
        this.hotel_admin = hotel_admin;
    }

    public int getId_user() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return is_admin;
    }

    public int getHotel_admin() {
        return hotel_admin;
    }
}
