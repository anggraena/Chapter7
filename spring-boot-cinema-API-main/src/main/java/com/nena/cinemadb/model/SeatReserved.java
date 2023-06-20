package com.nena.cinemadb.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "seat_reserved")
public class SeatReserved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatrsv_code;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "seat_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Seat seat;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Schedules schedules;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();


}
