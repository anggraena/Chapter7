package com.nena.cinemadb.model;

import com.nena.cinemadb.utils.Uuid;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "films")
public class Film {

    @Id
    @Column(name = "film_Code", updatable = false, insertable = false)
    private String filmCode = Uuid.uuidGenerator();


    @Column(name = "film_name")
    private String filmName;

    @Column(name = "airing_status")
    private Boolean airingStatus;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDate createdAt = LocalDate.now();

}
