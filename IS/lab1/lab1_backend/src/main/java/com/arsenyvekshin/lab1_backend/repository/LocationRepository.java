package com.arsenyvekshin.lab1_backend.repository;

import com.arsenyvekshin.lab1_backend.entity.Coordinates;
import com.arsenyvekshin.lab1_backend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("select count(*) from Route r where r.from.id = ?1 or r.to.id = ?1")
    public long calcUsageNum(Long id);

}

