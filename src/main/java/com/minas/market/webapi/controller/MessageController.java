package com.minas.market.webapi.controller;

import com.minas.market.domain.interfaces.MessageService;
import com.minas.market.infrastructure.mapper.MessageMapper;
import com.minas.market.infrastructure.persistence.entity.MessageEntity;
import com.minas.market.webapi.contract.MessageAPI;
import com.minas.market.webapi.dto.Message;
import com.minas.market.webapi.dto.request.MessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class MessageController implements MessageAPI {

    private final MessageMapper messageMapper;

    private final MessageService messageService;

    public MessageController(MessageMapper messageMapper, MessageService messageService) {
        this.messageMapper = messageMapper;
        this.messageService = messageService;
    }

    @Override
    public ResponseEntity<UUID> create(MessageRequest messageRequest) {
        UUID messageId = messageService.create(messageRequest).getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(messageId)
                .toUri();

        return ResponseEntity.created(uri).body(messageId);
    }

    @Override
    public ResponseEntity<UUID> update(UUID id, MessageRequest messageRequest) {
        return ResponseEntity.ok(messageService.update(id, messageRequest).getId());
    }

    @Override
    public ResponseEntity<Message> getOne(UUID id) {
        Message message = messageMapper.toDTO(messageService.findById(id));
        readMessage(id);
        return ResponseEntity.ok(message);
    }

    @Override
    public void delete(UUID id) {
        messageService.delete(id);
    }

    @Override
    public ResponseEntity<List<Message>> getAllMessagesByUserOrAnnouncements(UUID userId, UUID announcementId) {
        List<MessageEntity> allMessagesByUserOrAnnouncements = messageService.getAllMessagesByUserOrAnnouncements(
                userId, announcementId
        );
        List<Message> list = allMessagesByUserOrAnnouncements.stream().map(messageMapper::toDTO).toList();
        return ResponseEntity.ok(list);
    }

    @Override
    public void readMessage(UUID id) {
        messageService.readMessage(id);
    }
}
