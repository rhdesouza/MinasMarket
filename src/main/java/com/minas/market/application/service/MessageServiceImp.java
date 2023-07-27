package com.minas.market.application.service;

import com.minas.market.application.service.security.UserServiceImp;
import com.minas.market.domain.interfaces.AnnouncementService;
import com.minas.market.domain.interfaces.MessageService;
import com.minas.market.infrastructure.mapper.MessageMapper;
import com.minas.market.infrastructure.persistence.entity.MessageEntity;
import com.minas.market.infrastructure.persistence.repository.MessageRepository;
import com.minas.market.webapi.dto.request.MessageRequest;
import com.minas.market.webapi.exception.BusinessRuleException;
import com.minas.market.webapi.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImp implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private AnnouncementService announcementService;

    @Override
    @Transactional
    public MessageEntity create(MessageRequest messageRequest) {
        validateUserAndAnnouncements(messageRequest);
        MessageEntity entity = messageMapper.toEntity(messageRequest);
        entity.setRead(false);
        entity.setDeleted(false);
        return messageRepository.save(entity);
    }

    @Override
    @Transactional
    public MessageEntity update(UUID id, MessageRequest messageRequest) {
        validateUserAndAnnouncements(messageRequest);
        MessageEntity messageEntity = findById(id);
        messageEntity.setMessage(messageRequest.getMessage());
        return messageRepository.save(messageEntity);
    }

    private void validateUserAndAnnouncements(MessageRequest messageRequest) {
        if (userServiceImp.findUserById(messageRequest.getUserId()).isEmpty()) {
            throw new BusinessRuleException("User not found");
        }
        announcementService.findById(messageRequest.getAnnouncementId());
    }

    @Override
    public MessageEntity findById(UUID id) {
        return messageRepository.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        messageRepository.deleteMessageLogic(id);
    }

    @Override
    public List<MessageEntity> getAllMessagesByUserOrAnnouncements(UUID userId, UUID announcementId) {
        if (userId != null && announcementId == null) {
            return messageRepository.findAllByUserId(userId);
        }
        if (userId == null && announcementId != null) {
            return messageRepository.findAllByAnnouncementId(announcementId);
        }
        if (userId != null && announcementId != null) {
            return messageRepository.findAllByUserIdAndAnnouncementId(userId, announcementId);
        }
        return List.of();
    }

    @Override
    @Transactional
    public void readMessage(UUID id) {
        messageRepository.readMessage(id);
    }
}
