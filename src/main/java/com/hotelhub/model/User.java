package com.hotelhub.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final int id_user;
    private final String name;
    private final String email;
    private final String password;
    private final boolean is_admin;
    private final int hotel_admin;

    public User(int id_user, String name, String email, String password, boolean is_admin, int hotel_admin) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.password = password;
        this.is_admin = is_admin;
        this.hotel_admin = hotel_admin;
    }

    public int getId_user() {
        return id_user;
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
