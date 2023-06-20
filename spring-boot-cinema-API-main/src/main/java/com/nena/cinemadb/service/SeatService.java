package com.nena.cinemadb.service;

import com.nena.cinemadb.repository.SeatRepository;
import com.nena.cinemadb.response.ResponseHandler;
import com.nena.cinemadb.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {
    @Autowired
    SeatRepository seatRepo;

    ResponseHandler handler = new ResponseHandler();
    public ResponseEntity<Object> getAllSeat(){
        try{
            if(seatRepo.findAll().isEmpty()){
                return handler.generateResponse("no data", HttpStatus.NO_CONTENT, null);
            }
            return handler.generateResponse("data", HttpStatus.OK, seatRepo.findAllByOrderByStudioNameAsc());
        }catch (Exception e){
            return handler.generateResponse(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    public ResponseEntity<Object> addSeats(){
        String[] seatRow = new String[]{"A", "B", "C", "D", "E"};
        Integer[] seatNumber = new Integer[]{1,2,3,4,5};
        String[] studioName = new String[]{"A","B","C"};

        List<Seat> seatList = new ArrayList<>();

        try{
            for (int i = 0; i < studioName.length; i++) {
                for (int j = 0; j < seatRow.length; j++) {
                    for (int k = 0; k < seatNumber.length; k++) {
                        Seat seat = new Seat();
                        seat.setSeatRow(seatRow[j]);
                        seat.setSeatNumber(seatNumber[k]);
                        seat.setStudioName(studioName[i]);
                        seatList.add(seat);
                    }
                }
            }
            return handler.generateResponse("data", HttpStatus.OK, seatRepo.saveAll(seatList));

        }catch(Exception e){
            return handler.generateResponse(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    //GET AVAILABLE SEAT
    public ResponseEntity<Object> getAvailableSeats(String scheduleId){
        try{
            return ResponseHandler.generateResponse("data", HttpStatus.OK, seatRepo.getAvailableSeat(scheduleId));
        }catch (Exception e){
            return handler.generateResponse(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
