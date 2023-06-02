package com.challenge.mycinema.repository;

import com.challenge.mycinema.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    @Query(value = "SELECT s FROM Schedule s WHERE scheduleId = :scheduleId")
    Schedule getById(@Param("scheduleId") String scheduleId);

    @Query(value = "SELECT s FROM Schedule s WHERE filmId = :filmId")
    List<Schedule> getByFilm(@Param("filmId") String filmId);

}