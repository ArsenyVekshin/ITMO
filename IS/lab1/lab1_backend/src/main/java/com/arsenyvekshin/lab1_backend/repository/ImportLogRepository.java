package com.arsenyvekshin.lab1_backend.repository;

import com.arsenyvekshin.lab1_backend.entity.ImportLogNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportLogRepository extends JpaRepository<ImportLogNote, Long> {
}
