package com.minas.market.application.service;

import com.minas.market.domain.interfaces.PublicService;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PublicServiceImp implements PublicService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public List<AnnouncementEntity> findAnnouncements(String title) {
        return announcementRepository.findAllLikeDescription(title.toUpperCase());
    }
}
