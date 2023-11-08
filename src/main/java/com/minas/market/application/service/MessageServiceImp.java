package com.minas.market.application.service;

import com.minas.market.application.service.security.UserServiceImp;
import com.minas.market.domain.interfaces.AnnouncementService;
import com.minas.market.domain.interfaces.MessageService;
import com.minas.market.infrastructure.mapper.MessageMapper;
import com.minas.market.infrastructure.persistence.entity.MessageEntity;
import com.minas.market.infrastructure.persistence.repository.MessageRepository;
import com.minas.market.webapi.dto.request.MessageRequest;
import com.minas.market.webapi.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class MessageServiceImp implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserServiceImp userServiceImp;
    private final AnnouncementService announcementService;
    private final UserAuthenticatedServiceImp authenticatedUser;

    public MessageServiceImp(MessageRepository messageRepository, MessageMapper messageMapper, UserServiceImp userServiceImp, AnnouncementService announcementService, UserAuthenticatedServiceImp authenticatedUser) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.userServiceImp = userServiceImp;
        this.announcementService = announcementService;
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    @Transactional
    public MessageEntity create(MessageRequest messageRequest) {
        UUID userId = authenticatedUser.me().getId();
        validateUserAndAnnouncements(messageRequest, userId);
        MessageEntity entity = messageMapper.toEntity(messageRequest, userId);
        entity.setRead(false);
        entity.setDeleted(false);
        return messageRepository.save(entity);
    }

    @Override
    @Transactional
    public MessageEntity update(UUID id, MessageRequest messageRequest) {
        UUID userId = authenticatedUser.me().getId();
        validateUserAndAnnouncements(messageRequest, userId);
        MessageEntity messageEntity = findById(id);
        messageEntity.setMessage(messageRequest.getMessage());
        return messageRepository.save(messageEntity);
    }

    private void validateUserAndAnnouncements(MessageRequest messageRequest, UUID userId) {
        if (userServiceImp.findUserById(userId).isEmpty()) {
            throw new NotFoundException("User not found");
        }
        announcementService.findById(messageRequest.getAnnouncementId());
    }

    @Override
    public MessageEntity findById(UUID id) {
        //return messageRepository.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
        return findByIdAssync(id).orElseThrow(() -> new NotFoundException("Message not found"));
    }

    @Async
    private Optional<MessageEntity> findByIdAssync(UUID id) {
        CompletableFuture<Optional<MessageEntity>> entity = CompletableFuture.supplyAsync(() -> messageRepository.findById(id));
        CompletableFuture.allOf(entity);
        try {
            return entity.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        messageRepository.deleteMessageLogic(id);
    }

    @Override
    public List<MessageEntity> getAllMessagesByUserOrAnnouncements(UUID userId, UUID announcementId) {
        String selectQuery = compareNull(userId).concat(compareNull(announcementId));
        return switch (selectQuery) {
            case "10" -> messageRepository.findAllByUserId(userId);
            case "01" -> messageRepository.findAllByAnnouncementId(announcementId);
            case "11" -> messageRepository.findAllByUserIdAndAnnouncementId(userId, announcementId);
            default -> List.of();
        };
    }

    private String compareNull(UUID uuid) {
        return uuid == null ? "0" : "1";
    }

    @Override
    @Transactional
    public void readMessage(UUID id) {
        messageRepository.readMessage(id);
    }
}
