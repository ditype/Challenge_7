package com.challenge.mycinema.repository;

import com.challenge.mycinema.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio, String> {
    @Query(value = "SELECT s FROM Studio s WHERE studioId = :studioId")
    Studio getById(@Param("studioId") String studioId);

}