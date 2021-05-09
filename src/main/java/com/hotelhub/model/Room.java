package com.hotelhub.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int room_id;

    private int price;

    private String name;

    private int hotel_id;

    private int no_of_people;

    private String room_type;
}
