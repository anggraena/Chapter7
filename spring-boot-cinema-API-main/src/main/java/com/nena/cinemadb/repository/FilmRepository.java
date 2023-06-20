package com.nena.cinemadb.repository;

import com.nena.cinemadb.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, String> {
    @Query("SELECT f FROM Film f WHERE f.airingStatus = true")
    List<Film> getNowShowingFilms();

    @Query("SELECT f FROM Film f WHERE f.airingStatus = false")
    List<Film> getComingSoonFilms();


}
