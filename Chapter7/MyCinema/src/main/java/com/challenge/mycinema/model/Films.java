package com.challenge.mycinema.model;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "films")
public class Films {
    @Id
    @Column(name = "film_id", length = 36, unique = true)
    private String filmId;

    @Column(name = "genre_id", length = 36, columnDefinition = "VARCHAR(36) REFERENCES genre_film(genre_id)")
    private String genreId;

    @Column(name = "kode", length = 15, nullable = false)
    private String kode;

    @Column(name = "judul", length = 100, nullable = false)
    private String judul;

    @Column(name = "kategori", length = 20, nullable = false)
    private String kategori;

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