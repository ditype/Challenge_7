package com.challenge.mycinema.model;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "booking_id", length = 36, unique = true)
    private String bookingId;

    @Column(name = "schedule_id", length = 36, columnDefinition = "VARCHAR(36) REFERENCES schedule(schedule_id)")
    private String scheduleId;

    @Column(name = "user_id", length = 36, columnDefinition = "VARCHAR(36) REFERENCES users(user_id)")
    private String userId;

    @Column(name = "no_kursi", length = 5, nullable = false)
    private String noKursi;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "jml_tiket", columnDefinition = "NUMERIC DEFAULT 0")
    private Integer jmlTiket;

    @Column(name = "total_hrg", columnDefinition = "NUMERIC DEFAULT 0")
    private BigDecimal totalHrg;

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