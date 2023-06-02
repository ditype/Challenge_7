package com.challenge.mycinema.repository;

import com.challenge.mycinema.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    @Query(value = "SELECT u FROM Users u WHERE userId = :userId")
    Users getById(@Param("userId") String userId);

}