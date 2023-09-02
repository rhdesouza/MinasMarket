package com.minas.market.domain.interfaces;

import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.webapi.dto.request.AnnouncementRequest;

import java.util.List;
import java.util.UUID;

public interface AnnouncementService {
    AnnouncementEntity create(AnnouncementRequest announcementRequest);

    AnnouncementEntity update(UUID announcementId, AnnouncementRequest announcementRequest);

    AnnouncementEntity findById(UUID id);

    List<AnnouncementEntity> findAllByUserId();

    void delete(UUID id);
}
