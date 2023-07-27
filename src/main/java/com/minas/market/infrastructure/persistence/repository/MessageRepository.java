package com.minas.market.infrastructure.persistence.repository;

import com.minas.market.infrastructure.persistence.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    List<MessageEntity> findAllByUserId(UUID userId);

    List<MessageEntity> findAllByAnnouncementId(UUID announcementId);

    List<MessageEntity> findAllByUserIdAndAnnouncementId(UUID userId, UUID announcementId);

    @Modifying
    @Query("UPDATE MessageEntity m SET m.deleted = TRUE WHERE m.id = :id")
    void deleteMessageLogic(@Param(value = "id") UUID id);

    @Modifying
    @Query("UPDATE MessageEntity m SET m.read = TRUE WHERE m.id = :id")
    void readMessage(@Param(value = "id") UUID id);

}
