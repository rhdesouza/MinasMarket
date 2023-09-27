package com.minas.market.application.service;

import com.minas.market.domain.interfaces.AnnouncementService;
import com.minas.market.domain.interfaces.MessageService;
import com.minas.market.infrastructure.mapper.AnnouncementMapper;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.entity.enums.AnnouncementCategory;
import com.minas.market.infrastructure.persistence.repository.AnnouncementRepository;
import com.minas.market.webapi.dto.request.AnnouncementRequest;
import com.minas.market.webapi.exception.BusinessRuleException;
import com.minas.market.webapi.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnnouncementServiceImp implements AnnouncementService {

    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    AnnouncementMapper announcementMapper;
    @Autowired
    @Lazy
    MessageService messageService;
    @Autowired
    UserAuthenticatedServiceImp authenticatedUser;

    @Override
    @Transactional
    public AnnouncementEntity create(AnnouncementRequest announcementRequest) {
        AnnouncementEntity entity = announcementMapper.toEntity(announcementRequest, authenticatedUser.me().getId());
        entity.setActive(true);
        return announcementRepository.save(entity);
    }

    @Override
    @Transactional
    public AnnouncementEntity update(UUID announcementId, AnnouncementRequest announcementRequest) {
        validateAnnouncementFromUser(announcementId);
        AnnouncementEntity entity = announcementMapper.toEntity(announcementRequest, authenticatedUser.me().getId());
        entity.setId(announcementId);
        entity.setCategory(AnnouncementCategory.getEnum(announcementRequest.getCategory().name()));
        entity.setDescription(announcementRequest.getDescription());
        entity.setSaleValue(announcementRequest.getSaleValue());
        return announcementRepository.save(entity);
    }

    @Override
    public AnnouncementEntity findById(UUID announcementId) {
        AnnouncementEntity announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException("Announcement not found"));
        announcement.setMessages(messageService.getAllMessagesByUserOrAnnouncements(null, announcementId));
        return announcement;
    }

    @Override
    public List<AnnouncementEntity> findAllByUserId() {
        return announcementRepository.findAllByUserId(authenticatedUser.me().getId());
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        validateAnnouncementFromUser(id);
        AnnouncementEntity announcement = findById(id);
        announcement.setActive(false);
        announcementRepository.save(announcement);
    }

    private void validateAnnouncementFromUser(UUID announcementId) {
        announcementRepository.findByIdAndUserId(announcementId, authenticatedUser.me().getId())
                .orElseThrow(() -> new BusinessRuleException("Announcement does not belong to the user"));
    }
}
