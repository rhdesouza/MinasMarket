package com.minas.market.infrastructure.persistence.repository;

import com.minas.market.infrastructure.persistence.entity.MessageEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    @Query("FROM MessageEntity me WHERE me.userId = :userId AND me.deleted = false")
    List<MessageEntity> findAllByUserId(@Param(value = "userId") @NotNull UUID userId);

    @Query("FROM MessageEntity me WHERE me.announcementId = :announcementId AND me.deleted = false")
    List<MessageEntity> findAllByAnnouncementId(UUID announcementId);

    @Query("FROM MessageEntity me WHERE me.userId = :userId AND me.announcementId = :announcementId AND me.deleted = false")
    List<MessageEntity> findAllByUserIdAndAnnouncementId(UUID userId, UUID announcementId);

    @Modifying
    @Query("UPDATE MessageEntity m SET m.deleted = TRUE WHERE m.id = :id")
    void deleteMessageLogic(@Param(value = "id") UUID id);

    @Modifying
    @Query("UPDATE MessageEntity m SET m.read = TRUE WHERE m.id = :id")
    void readMessage(@Param(value = "id") UUID id);

}
