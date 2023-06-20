package com.nena.cinemadb.model;

import com.nena.cinemadb.utils.Uuid;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "seat")
public class Seat {
    @Id
    @Column(name = "seat_code")
    private String seatCode = Uuid.uuidGenerator();

    @Column(name = "seat_row")
    private String seatRow;

    @Column(name = "Seat_number")
    private int seatNumber;
    @Column(name = "studio_name")
    private String studioName;




}
