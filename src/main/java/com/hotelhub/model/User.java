package com.hotelhub.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_user;

    private String name;

    private String email;

    private String password;

    private boolean is_admin;

    private int hotel_admin;
}
