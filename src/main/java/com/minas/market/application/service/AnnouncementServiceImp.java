package com.minas.market.application.service;

import com.minas.market.application.service.security.UserServiceImp;
import com.minas.market.domain.interfaces.AnnouncementService;
import com.minas.market.infrastructure.mapper.AnnouncementMapper;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.entity.enums.AnnouncementCategory;
import com.minas.market.infrastructure.persistence.repository.AnnouncementRepository;
import com.minas.market.webapi.dto.request.AnnouncementRequest;
import com.minas.market.webapi.exception.BusinessRuleException;
import com.minas.market.webapi.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnnouncementServiceImp implements AnnouncementService {

    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    AnnouncementMapper announcementMapper;

    @Override
    @Transactional
    public AnnouncementEntity create(AnnouncementRequest announcementRequest) {
        if (userServiceImp.findUserById(announcementRequest.getUserId()).isEmpty()) {
            throw new BusinessRuleException("User not found");
        }
        AnnouncementEntity entity = announcementMapper.toEntity(announcementRequest);
        entity.setActive(true);
        return announcementRepository.save(entity);
    }

    @Override
    @Transactional
    public AnnouncementEntity update(UUID announcementId, AnnouncementRequest announcementRequest) {
        if (userServiceImp.findUserById(announcementRequest.getUserId()).isEmpty()) {
            throw new BusinessRuleException("User not found");
        }
        AnnouncementEntity announcement = findById(announcementId);
        announcement.setCategory(AnnouncementCategory.getEnum(announcementRequest.getCategory().name()));
        announcement.setDescription(announcementRequest.getDescription());
        announcement.setSaleValue(announcementRequest.getSaleValue());
        return announcementRepository.save(announcement);
    }

    @Override
    public AnnouncementEntity findById(UUID announcementId) {
        return announcementRepository.findById(announcementId).orElseThrow(() -> new NotFoundException("Announcement not found"));
    }

    @Override
    public List<AnnouncementEntity> findAllByUserId(UUID userId) {
        return announcementRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AnnouncementEntity announcement = findById(id);
        announcement.setActive(false);
        announcementRepository.save(announcement);
    }
}
