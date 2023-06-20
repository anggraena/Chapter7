package com.nena.cinemadb.repository;

import com.nena.cinemadb.model.SeatReserved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatReservedRepository extends JpaRepository<SeatReserved, String> {
}
