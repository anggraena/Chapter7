package com.nena.cinemadb.repository;

import com.nena.cinemadb.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findAllByOrderByStudioNameAsc();


    @Query("SELECT s FROM Seat s WHERE s.studioName = (SELECT sc.studioName FROM Schedules sc WHERE sc.scheduleId = ?1) AND s.seatCode NOT IN (SELECT sr.seat.seatCode FROM SeatReserved sr WHERE sr.schedules.scheduleId = ?1) ORDER BY s.seatRow ASC")
    List<Seat> getAvailableSeat(String scheduleId);
}
