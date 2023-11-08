package com.minas.market.unit.application.service;

import com.minas.market.application.service.PublicServiceImp;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.repository.AnnouncementRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class PublicServiceImpTest {

    @Autowired
    private PublicServiceImp publicServiceImp;

    @MockBean
    private AnnouncementRepository announcementRepository;

    @Test
    @DisplayName("Should return the list of ads filtered by title")
    void getAnnouncements() {
        List<AnnouncementEntity> announcementEntitiesMock = new EasyRandom()
                .objects(AnnouncementEntity.class, 3)
                .toList();

        Mockito.when(announcementRepository.findAllLikeDescription(any(String.class))).thenReturn(announcementEntitiesMock);
        List<AnnouncementEntity> announcements = publicServiceImp.findAnnouncements("JAVA");
        assertEquals(3, announcements.size());
    }

}
