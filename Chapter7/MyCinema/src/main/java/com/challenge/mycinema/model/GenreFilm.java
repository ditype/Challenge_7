package com.challenge.mycinema.model;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "genre_film")
public class GenreFilm {
    @Id
    @Column(name = "genre_id", length = 36, unique = true)
    private String genreId;

    @Column(name = "kode", length = 5, nullable = false)
    private String kode;

    @Column(name = "nama", length = 50, nullable = false)
    private String nama;

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