package com.challenge.mycinema.repository;

import com.challenge.mycinema.model.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, String> {
    @Query(value = "SELECT s FROM Seats s WHERE seatId = :seatId")
    Seats getById(@Param("seatId") String seatId);

}