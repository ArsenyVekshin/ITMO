package com.arsenyvekshin.lab1_backend.repository;

import com.arsenyvekshin.lab1_backend.entity.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {

    @Query("select count(*) from Route r where r.coordinates.id = ?1")
    public long calcUsageNum(Long id);

}

