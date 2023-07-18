package com.minas.market.infrastructure.persistence.repository;

import com.minas.market.infrastructure.persistence.entity.AnnouncementImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnnouncementImageRepository extends JpaRepository<AnnouncementImageEntity, UUID> {
    List<AnnouncementImageEntity> findAllAnnouncementImageEntityByAnnouncementId(UUID announcementId);

    @Query("FROM AnnouncementImageEntity est WHERE est.announcementId = :announcementId")
    List<AnnouncementImageEntity> findAllByAnnouncementId(@Param(value = "announcementId") UUID announcementId);

}
