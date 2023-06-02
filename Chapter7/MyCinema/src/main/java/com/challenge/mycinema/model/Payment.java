package com.challenge.mycinema.model;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "payment_id", length = 36, unique = true)
    private String paymentId;

    @Column(name = "booking_id", length = 36, columnDefinition = "VARCHAR(36) REFERENCES booking(booking_id)")
    private String bookingId;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "total_payment", columnDefinition = "NUMERIC DEFAULT 0")
    private BigDecimal totalPayment;

    @Column(name = "payment_method", length = 30, nullable = true)
    private String paymentMethod;

    @Column(name = "status", length = 30, nullable = true, columnDefinition = "VARCHAR(30) DEFAULT 'On Process'")
    private String status;

    @Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "created_by", length = 100, columnDefinition = "VARCHAR(100) DEFAULT 'SYSTEM'")
    private String createdBy;

    @Column(name = "updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Column(name = "updated_by", length = 100, columnDefinition = "VARCHAR(100) DEFAULT 'SYSTEM'")
    private String updatedBy;

    @Column(name = "isactive", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
    private String isactive;

}