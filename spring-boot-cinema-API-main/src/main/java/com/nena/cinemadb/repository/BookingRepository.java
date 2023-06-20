package com.nena.cinemadb.repository;

import com.nena.cinemadb.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT b.createdAt, b.bookingCode, " +
            "b.user.email, " +
            "(SELECT f.filmName from Schedules s INNER JOIN Film as f on s.film.filmCode = f.filmCode WHERE s.scheduleId = b.schedules.scheduleId), " +
            "s.showingDate, s.timeStart, s.timeEnd FROM Booking b " +
            "INNER JOIN Schedules s ON b.schedules.scheduleId = s.scheduleId WHERE b.bookingCode = ?1")
    Object getDetailBooking(String bookingCode);

//    @Query("SELECT b FROM Booking b WHERE b.bookingCode = ?1")

    List<Booking> findByBookingCode(String bookingCode);
}
