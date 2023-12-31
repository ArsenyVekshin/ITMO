package ArsenyVeksin.lab4.backend.db;

import ArsenyVeksin.lab4.backend.entities.HitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<HitEntity, Long> {
    List<HitEntity> findAllByOwnerId(Long id);
    void deleteAllByOwnerId(Long id);

    @Modifying
    @Query("UPDATE HitEntity h SET h.removed=true WHERE h.ownerId= ?1")
    void markAllRemovedByOwnerId(Long id);
}

