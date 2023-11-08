package com.minas.market.application.service;

import com.minas.market.domain.interfaces.PublicService;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicServiceImp implements PublicService {

    private final AnnouncementRepository announcementRepository;

    public PublicServiceImp(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public List<AnnouncementEntity> findAnnouncements(String title) {
        return announcementRepository.findAllLikeDescription(title.toUpperCase());
    }
}
