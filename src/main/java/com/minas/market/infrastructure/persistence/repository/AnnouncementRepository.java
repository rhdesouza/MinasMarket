package com.minas.market.infrastructure.persistence.repository;

import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, UUID> {
    List<AnnouncementEntity> findAllByUserId(UUID userId);

    @NotNull
    @Query("FROM AnnouncementEntity ae JOIN FETCH ae.messages me WHERE ae.id = :announcementId and me.deleted = false")
    Optional<AnnouncementEntity> findById(@Param(value = "announcementId") @NotNull UUID userId);

}
