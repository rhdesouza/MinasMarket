package com.minas.market.domain.interfaces;

import com.minas.market.infrastructure.persistence.entity.MessageEntity;
import com.minas.market.webapi.dto.request.MessageRequest;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageEntity create(MessageRequest messageRequest);

    MessageEntity update(UUID id, MessageRequest messageRequest);

    MessageEntity findById(UUID id);

    void delete(UUID id);

    List<MessageEntity> getAllMessagesByUserOrAnnouncements(UUID userId, UUID announcementId);

    void readMessage(UUID id);
}
