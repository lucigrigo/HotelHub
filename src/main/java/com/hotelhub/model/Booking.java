package com.hotelhub.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_reservation;

    private String room;

    private int room_id;

    private long user_id;

    private boolean approved;

    private Date date_start;

    private Date date_end;
}
