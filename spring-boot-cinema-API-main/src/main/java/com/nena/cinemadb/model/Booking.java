package com.nena.cinemadb.model;

import com.nena.cinemadb.utils.Uuid;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "booking_code", updatable = false, insertable = false)
    private String bookingCode = Uuid.uuidGenerator();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "seat_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Seat seat;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Schedules schedules;


    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();


}
