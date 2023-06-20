package com.nena.cinemadb.repository;

import com.nena.cinemadb.model.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, String> {

    @Query("SELECT f.filmName, f.filmCode, s  FROM Schedules s, Film f WHERE s.film.filmCode =?1 and f.filmCode =?1")
    List<Object> findByFilmCode(String filmCode);
}
