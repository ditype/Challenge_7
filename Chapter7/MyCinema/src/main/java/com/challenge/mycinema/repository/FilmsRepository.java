package com.challenge.mycinema.repository;

import com.challenge.mycinema.model.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmsRepository extends JpaRepository<Films, String> {
    @Query(value = "SELECT f FROM Films f WHERE filmId = :filmId")
    Films getById(@Param("filmId") String filmId);

    @Query(value = "SELECT f FROM Films f WHERE genreId = :genreId")
    List<Films> getByGenre(@Param("genreId") String genreId);

}