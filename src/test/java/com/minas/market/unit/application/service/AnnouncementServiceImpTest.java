package com.minas.market.unit.application.service;

import com.minas.market.application.service.AnnouncementServiceImp;
import com.minas.market.application.service.security.UserServiceImp;
import com.minas.market.infrastructure.mapper.AnnouncementMapper;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.entity.security.User;
import com.minas.market.infrastructure.persistence.repository.AnnouncementRepository;
import com.minas.market.webapi.dto.request.AnnouncementRequest;
import com.minas.market.webapi.exception.NotFoundException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AnnouncementServiceImpTest {

    @Autowired
    private AnnouncementServiceImp announcementServiceImp;
    @Autowired
    private AnnouncementMapper announcementMapper;
    @MockBean
    private AnnouncementRepository announcementRepository;
    @MockBean
    private UserServiceImp userServiceImp;
    private UUID userId;
    private UUID announcementId;
    private AnnouncementRequest announcementRequest;

    @BeforeEach
    void onInit() {
        announcementRequest = new EasyRandom().nextObject(AnnouncementRequest.class);
        announcementId = UUID.randomUUID();
        userId = announcementRequest.getUserId();
    }

    @Test
    @DisplayName("Should create announcement when data is correct")
    void create_onSuccess() {
        AnnouncementEntity expected = announcementMapper.toEntity(announcementRequest);
        Mockito.when(announcementRepository.save(any())).thenReturn(expected);
        Mockito.when(userServiceImp.findUserById(userId)).thenReturn(Optional.of(new User()));
        AnnouncementEntity created = announcementServiceImp.create(announcementRequest);

        assertEquals(expected, created);
    }

    @Test
    @DisplayName("Should updated announcement when data is correct")
    void update_onSuccess() {
        AnnouncementEntity expected = announcementMapper.toEntity(announcementRequest);
        Mockito.when(userServiceImp.findUserById(userId)).thenReturn(Optional.of(new User()));
        Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(expected));
        Mockito.when(announcementRepository.save(any())).thenReturn(expected);
        AnnouncementEntity created = announcementServiceImp.update(announcementId, announcementRequest);
        assertEquals(expected, created);
    }

    @Test
    @DisplayName("Should find announcement when exists")
    void findById_onSuccess() {
        AnnouncementEntity expected = announcementMapper.toEntity(announcementRequest);
        Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(expected));
        AnnouncementEntity localized = announcementServiceImp.findById(announcementId);

        assertEquals(expected, localized);
    }

    @Test
    @DisplayName("Should throw error when announcement is not found")
    void findById_onError() {
        Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> announcementServiceImp.findById(announcementId));

        assertEquals("Announcement not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should find all announcements by user id")
    void findAllByUserId() {
        List<AnnouncementEntity> announcementEntitiesMock = new EasyRandom()
                .objects(AnnouncementEntity.class, 3)
                .toList();
        Mockito.when(announcementRepository.findAllByUserId(userId)).thenReturn(announcementEntitiesMock);

        List<AnnouncementEntity> localized = announcementServiceImp.findAllByUserId(userId);

        assertEquals(3, localized.size());
    }

    @Test
    @DisplayName("Should deleted announcement when id is valid")
    void delete() {
        AnnouncementEntity expected = announcementMapper.toEntity(announcementRequest);
        Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(expected));

        assertDoesNotThrow(() -> announcementServiceImp.delete(announcementId));
    }

}
