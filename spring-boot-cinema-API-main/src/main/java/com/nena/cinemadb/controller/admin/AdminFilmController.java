package com.nena.cinemadb.controller.admin;

import com.nena.cinemadb.model.Film;
import com.nena.cinemadb.model.Schedules;
import com.nena.cinemadb.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminFilmController {
    @Autowired
    FilmService service;



    //ADD NEW FILM
    @PostMapping("/film")
    public ResponseEntity<Object> addNewFilm(@RequestBody Film newFilm){
        return service.addFilm(newFilm);
    }

    //DELETE FILM
    @DeleteMapping("/film/{filmCode}")
    public ResponseEntity<Object> removeFilm(@PathVariable String filmCode){
        return service.deleteFilm(filmCode);
    }

    //UPDATE FILM
    @PatchMapping("/film/{filmCode}")
    public ResponseEntity<Object> updateFilm(@PathVariable String filmCode, @RequestBody Film film){
        return service.updateFilm(filmCode, film);
    }

    //ADD NEW SCHEDULES
    @PostMapping("/schedules")
    public ResponseEntity<Object> addNewSchedules(@RequestBody Schedules schedules){
        return service.addNewSchedule(schedules);
    }


}

