package com.nena.cinemadb.service;

import com.nena.cinemadb.repository.SeatReservedRepository;
import com.nena.cinemadb.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SeatReservedService {
    @Autowired
    SeatReservedRepository repo;
    public ResponseEntity<Object> getSeatReserved(){
        try{
            return ResponseHandler.generateResponse("success", HttpStatus.OK, repo.findAll());
        }catch (Exception e){
            return ResponseHandler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
        }
    }
}
