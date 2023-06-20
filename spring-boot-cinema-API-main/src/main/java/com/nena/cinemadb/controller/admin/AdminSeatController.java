package com.nena.cinemadb.controller.admin;

import com.nena.cinemadb.service.SeatReservedService;
import com.nena.cinemadb.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
public class AdminSeatController {
    @Autowired
    SeatService seatService;

    @Autowired
    SeatReservedService srService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllSeat(){
        return seatService.getAllSeat();
    }

    @PostMapping("/initiate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> addSeat(){
        return  seatService.addSeats();
    }


    @GetMapping("/available/{scheduleId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getAvailableSeats(@PathVariable String scheduleId){
        return  seatService.getAvailableSeats(scheduleId);
    }


}
