package com.nena.cinemadb.controller.user;

import com.nena.cinemadb.model.Booking;
import com.nena.cinemadb.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//user
@RestController
@RequestMapping("/booking")
public class UserBookingController {
    @Autowired
    BookingService bookingService;

    //dont impleneted it first
//    @GetMapping()
//    public ResponseEntity<Object> getAllBooking(){
//        return bookingService.getAllBooking();
//    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> addNewBooking(@RequestHeader(value = "Authorization") String token,
                                                @RequestBody Booking booking){
        return bookingService.addNewBooking(token,booking);
    }

    @GetMapping("/detail/{bookingCode}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getDetailBooking(@PathVariable String bookingCode){
        return bookingService.getBookingDetail(bookingCode);
    }
}
