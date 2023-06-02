package com.challenge.mycinema.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "user_id", length = 36, unique = true)
    private String userId;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "no_telp", length = 15, nullable = false)
    private String noTelp;

    @Column(name = "username", length = 100, nullable = false)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "level_user", length = 30, nullable = false)
    private String levelUser;

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