package com.minas.market.integration.webapi.helper;

import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.repository.AnnouncementRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.jeasy.random.FieldPredicates.named;

@Service
public class AnnoucementHelper {

    @Autowired
    AnnouncementRepository announcementRepository;

    public UUID createAnnouncement(UUID userId) {
        AnnouncementEntity announcementEntity = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("userId"), () -> userId)
                        .excludeField(named("id"))
                        .excludeField(named("createdBy"))
                        .excludeField(named("createdDate"))
                        .excludeField(named("lastModifiedBy"))
                        .excludeField(named("lastModifiedDate"))
                        .excludeField(named("images"))
        ).nextObject(AnnouncementEntity.class);
        return announcementRepository.save(announcementEntity).getId();
    }
}
