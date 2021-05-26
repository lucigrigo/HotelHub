package com.hotelhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_id;

    @Column(name = "room_name")
    private String room;

    @Column(name = "room_id")
    private int room_id;

    @Column(name = "user_id")
    private long user_id;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "date_start")
    private Date date_start;

    @Column(name = "date_end")
    private Date date_end;
}
