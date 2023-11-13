package com.minas.market.infrastructure.persistence.repository;

import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, UUID> {
    List<AnnouncementEntity> findAllByUserId(UUID userId);

    Optional<AnnouncementEntity> findByIdAndUserId(UUID id, UUID userId);

    @Query("FROM AnnouncementEntity es WHERE UPPER(es.title) LIKE %:title%")
    List<AnnouncementEntity> findAllLikeDescription(@Param(value = "title") String title);

}
