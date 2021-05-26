package com.hotelhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int room_id;

    @Column(name = "price")
    private int price;

    @Column(name = "name")
    private String name;

    @Column(name = "hotel_id")
    private int hotel_id;

    @Column(name = "no_of_people")
    private int no_of_people;

    @Column(name = "type")
    private String room_type;
}
