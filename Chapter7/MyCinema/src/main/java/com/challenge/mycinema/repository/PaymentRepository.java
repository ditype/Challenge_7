package com.challenge.mycinema.repository;

import com.challenge.mycinema.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query(value = "SELECT p FROM Payment p WHERE paymentId = :paymentId")
    Payment getById(@Param("paymentId") String paymentId);

    @Query(value = "SELECT p FROM Payment p WHERE bookingId = :bookingId")
    List<Payment> getByBooking(@Param("bookingId") String bookingId);

}