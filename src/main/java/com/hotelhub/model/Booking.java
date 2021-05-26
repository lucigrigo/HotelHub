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
    private String booking_id;

    @Column(name = "room")
    private Room room;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "to_be_canceled")
    private boolean to_be_canceled;

    @Column(name = "date_start")
    private Date date_start;

    @Column(name = "date_end")
    private Date date_end;
}
