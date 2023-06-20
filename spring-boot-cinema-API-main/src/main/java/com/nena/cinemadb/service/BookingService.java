package com.nena.cinemadb.service;

import com.nena.cinemadb.repository.BookingRepository;
import com.nena.cinemadb.repository.SeatReservedRepository;
import com.nena.cinemadb.repository.UserRepository;
import com.nena.cinemadb.response.ResponseHandler;
import com.nena.cinemadb.model.Booking;
import com.nena.cinemadb.model.SeatReserved;
import com.nena.cinemadb.model.User;
import com.nena.cinemadb.security.jwt.JwtUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    SeatReservedRepository seatReservedRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(BookingService.class);

    //get all booking
    public ResponseEntity<Object> getAllBooking(){
        try{
            logger.trace("");
            return ResponseHandler.generateResponse("data", HttpStatus.OK, bookingRepository.findAll());
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    //add booking
    public  ResponseEntity<Object> addNewBooking(String token, Booking booking){
        try{
            String jwtToken = token.substring(7, token.length());
            User userFound = null;
            if(jwtToken != null && jwtUtils.validateTokenJwt(jwtToken)){
                String username = jwtUtils.getUserNameFromToken(jwtToken);
                userFound = userRepository.findByUname(username).orElseThrow( () -> new UsernameNotFoundException("Uname not found"));

            }
            SeatReserved newSeatReserve = new SeatReserved();
            booking.setUser(userFound);
            newSeatReserve.setSeat(booking.getSeat());
            newSeatReserve.setSchedules(booking.getSchedules());
            seatReservedRepository.save(newSeatReserve);
            return ResponseHandler.generateResponse("data", HttpStatus.OK, bookingRepository.save(booking));
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    //get detail booking
    public ResponseEntity<Object> getBookingDetail(String bookingCodeParams){
        try{
            List<Booking> bookingData = bookingRepository.findByBookingCode(bookingCodeParams);
            File file = ResourceUtils.getFile("classpath:report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            Map parameters = new HashMap();
            parameters.put("Created By", "Kelompok 4");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bookingData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\Ivallavi\\Documents\\Stupend\\" + LocalDate.now() + ".pdf");

            return  ResponseHandler.generateResponse("Success", HttpStatus.OK, bookingData);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


}
