package com.challenge.mycinema.repository;

import com.challenge.mycinema.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query(value = "SELECT b FROM Booking b WHERE bookingId = :bookingId")
    Booking getById(@Param("bookingId") String bookingId);

    @Query(value = "SELECT b FROM Booking b WHERE scheduleId = :scheduleId")
    List<Booking> getBySchedule(@Param("scheduleId") String scheduleId);

}