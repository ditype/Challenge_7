package com.challenge.mycinema.model;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Entity
@Data
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "schedule_id", length = 36, unique = true)
    private String scheduleId;

    @Column(name = "film_id", length = 36, columnDefinition = "VARCHAR(36) REFERENCES films(film_id)")
    private String filmId;

    @Column(name = "studio_id", length = 36, columnDefinition = "VARCHAR(36) REFERENCES studio(studio_id)")
    private String studioId;

    @Column(name = "hrg_tiket", nullable = true, columnDefinition = "NUMERIC DEFAULT 0")
    private BigDecimal hrgTiket;

    @Column(name = "tgl_tayang")
    private Date tglTayang;

    @Column(name = "jam_mulai")
    private Time jamMulai;

    @Column(name = "jam_selesai")
    private Time jamSelesai;

    @Column(name = "[desc]", columnDefinition = "TEXT")
    private String desc;

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