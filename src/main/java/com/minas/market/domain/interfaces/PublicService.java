package com.minas.market.domain.interfaces;

import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;

import java.util.List;

public interface PublicService {
    List<AnnouncementEntity> findAnnouncements(String title);

}
