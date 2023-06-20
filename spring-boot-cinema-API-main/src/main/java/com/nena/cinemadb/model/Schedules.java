package com.nena.cinemadb.model;

import com.nena.cinemadb.utils.Uuid;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "schedules")
public class Schedules {
    @Id
    @Column(name = "schedule_id", updatable = false)
    private String scheduleId = Uuid.uuidGenerator();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "film_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Film film;

    @Column(name = "showing_date")
    private LocalDate showingDate;
    @Column(name = "time_start")
    private LocalTime timeStart;
    @Column(name = "time_end")
    private LocalTime timeEnd;

    @Column(name = "ticket_price")
    private int ticketPrice;

    @Column(name = "studio_name")
    private String studioName;
}
