package com.minas.market.domain.interfaces;

import com.minas.market.infrastructure.persistence.entity.AnnouncementImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AnnouncementImageService {

    AnnouncementImageEntity create(UUID announcementId, MultipartFile file);

    AnnouncementImageEntity findById(UUID id);

    List<AnnouncementImageEntity> getAllByAnnouncementId(UUID userId);

    void delete(UUID id);

}
