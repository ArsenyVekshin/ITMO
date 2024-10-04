package com.arsenyvekshin.lab1_backend.repositories;

import com.arsenyvekshin.lab1_backend.entities.Route;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    @Query("SELECT p FROM Route p WHERE p.owner.id = ?1 ORDER BY p.creationDate DESC")
    List<Route> findByUserId(Long userId);

    @Query("SELECT SUM(rating) FROM Route")
    int calculateTotalRating(); // Рассчитать сумму значений поля rating для всех объектов.

    @Query("SELECT p FROM Route p ORDER BY p.to.id DESC LIMIT 1")
    List<Route> findMaxTo(); // Вернуть один (любой) объект, значение поля to которого является максимальным

    @Query("SELECT p FROM Route p WHERE p.rating > ?1")
    List<Route> findRoutesWithGreaterRating(int rating); // Вернуть массив объектов, значение поля rating которых больше заданного.

    @Query("SELECT p FROM Route p WHERE p.from.id = ?1 AND p.to.id = ?2")
    List<Route> findAllRotesBy(Long from_location_id, Long to_location_id); // Найти все маршруты между указанными пользователем локациями, отсортировать список по заданному параметру.

    @Transactional
    @Modifying
    @Query("DELETE FROM Route p WHERE p.owner.id = ?1 and p.readonly=false")
    void clearByUserId(Long userId);


}
