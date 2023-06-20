package com.nena.cinemadb.service;

import com.nena.cinemadb.repository.FilmRepository;
import com.nena.cinemadb.repository.SchedulesRepository;
import com.nena.cinemadb.response.ResponseHandler;
import com.nena.cinemadb.model.Film;
import com.nena.cinemadb.model.Schedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilmService {
    @Autowired
    FilmRepository repo;

    @Autowired
    SchedulesRepository scheduleRepo;



    //GET FILM SCHEDULES
    public ResponseEntity<Object> getBySchedules(String filmCode){
        ResponseEntity<Object> response;
        List<Object> schedules = scheduleRepo.findByFilmCode(filmCode);
        if(repo.findById(filmCode).isEmpty()){
            response = ResponseHandler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
            return response;
        }
         response = ResponseHandler.generateResponse("success", HttpStatus.OK, schedules);
        return response;
    }


    //POST FILM
    public ResponseEntity<Object> addFilm(Film newFilm){
        try{
            repo.save(newFilm);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, newFilm);
        }catch (Exception e){
            return ResponseHandler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
        }
    }

    //DELETE FILM
    public ResponseEntity<Object> deleteFilm(String id){
        Optional<Film> filmFound = repo.findById(id);
        if(filmFound.isPresent()){
            repo.deleteById(id);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, null);
        }
        return ResponseHandler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
    }

    //UPDATE FILM
    public ResponseEntity<Object> updateFilm(String id, Film film){
        Optional<Film> filmFound = repo.findById(id);
        if(filmFound.isPresent()){
            Film updateFilm = filmFound.get();
            updateFilm.setFilmName(film.getFilmName());
            updateFilm.setAiringStatus(film.getAiringStatus());
            return ResponseHandler.generateResponse("success", HttpStatus.OK, repo.save(updateFilm));

        }
        return ResponseHandler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
    }

    //GET FILM THAT AIRING STATUS IS TRUE
    public ResponseEntity<Object> getFilmNowShowing(){
        try{
           List<Film> arrFilm = repo.getNowShowingFilms();
           List<Object> listMap = new ArrayList<>();
           arrFilm.stream().forEach(film -> {
               Map<String, Object> mapResult = new HashMap<>();
               mapResult.put("film_code", film.getFilmCode());
               mapResult.put("film_name", film.getFilmName());
               listMap.add(mapResult);
           });
            return ResponseHandler.generateResponse("success", HttpStatus.OK, listMap);
        }catch (Exception e){
            return ResponseHandler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
        }
    }

    //GET FILM THAT AIRING STATUS IS false
    public ResponseEntity<Object> getComingSoonFilm(){
        try{
            List<Film> arrFilm = repo.getComingSoonFilms();
            List<Object> listMap = new ArrayList<>();
            arrFilm.stream().forEach(film -> {
                Map<String, Object> mapResult = new HashMap<>();
                mapResult.put("film_code", film.getFilmCode());
                mapResult.put("film_name", film.getFilmName());
                listMap.add(mapResult);
            });
            return ResponseHandler.generateResponse("success", HttpStatus.OK, listMap);
        }catch (Exception e){
            return ResponseHandler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
        }
    }

    //ADD NEW SCHEDULES
    public ResponseEntity<Object> addNewSchedule(Schedules schedules){
        try{
            return ResponseHandler.generateResponse("success", HttpStatus.OK, scheduleRepo.save(schedules));
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }



}
