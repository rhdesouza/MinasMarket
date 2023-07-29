package com.minas.market.unit.application.service;

import com.minas.market.domain.interfaces.AnnouncementImageService;
import com.minas.market.domain.interfaces.AnnouncementService;
import com.minas.market.infrastructure.mapper.AnnouncementImageMapper;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.entity.AnnouncementImageEntity;
import com.minas.market.infrastructure.persistence.repository.AnnouncementImageRepository;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AnnouncementImageServiceImpTest {

    @Autowired
    private AnnouncementImageService announcementImageService;
    @Autowired
    private AnnouncementImageMapper announcementImageMapper;
    @MockBean
    private AnnouncementImageRepository announcementImageRepository;
    @MockBean
    private AnnouncementService announcementService;
    private UUID announcementId;
    private AnnouncementImageEntity announcementImageEntityMock;
    private MultipartFile fileMock;

    public AnnouncementImageServiceImpTest() {
    }

    @BeforeEach
    void onInit() throws IOException {
        announcementId = UUID.randomUUID();

        fileMock = new MockMultipartFile("test.jpg", "test.jpg", "jpg", HexFormat.ofDelimiter(":")
                .parseHex("e0:4f:d0:20:ea:3a:69:10:a2:d8:08:00:2b:30:30:9d"));

        announcementImageEntityMock = new AnnouncementImageEntity(
                UUID.randomUUID(),
                announcementId,
                fileMock.getName(),
                fileMock.getContentType(),
                fileMock.getSize(),
                fileMock.getBytes()
        );
    }

    @Test
    @DisplayName("Should create address when data is correct")
    void create_onSuccess() throws Exception {
        Mockito.when(announcementService.findById(any(UUID.class))).thenReturn(new EasyRandom().nextObject(AnnouncementEntity.class));
        Mockito.when(announcementImageRepository.save(any(AnnouncementImageEntity.class))).thenReturn(this.announcementImageEntityMock);

        AnnouncementImageEntity announcementImageEntity = announcementImageService.create(announcementId, fileMock);

        assertEquals(fileMock.getName(), announcementImageEntity.getFileName());
        assertEquals(fileMock.getContentType(), announcementImageEntity.getFileType());
        assertEquals(fileMock.getSize(), announcementImageEntity.getSize());
        assertEquals(announcementId, announcementImageEntity.getAnnouncementId());
        assertEquals(fileMock.getBytes(), announcementImageEntity.getData());
    }

    @Test
    @DisplayName("Should find image announcement when exists")
    void findById_onSuccess() {
        Mockito.when(announcementImageRepository.findById(announcementId)).thenReturn(Optional.of(announcementImageEntityMock));

        AnnouncementImageEntity announcementImageEntity = announcementImageService.findById(announcementId);

        assertEquals(announcementImageEntityMock, announcementImageEntity);
    }

    @Test
    @DisplayName("Should throw error when image announcement is not found")
    void findById_onError() {
        Mockito.when(announcementImageRepository.findById(announcementId)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> announcementImageService.findById(announcementId));

        assertEquals("Image announcement not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should find all images announcements by user id")
    void findAllByUserId() {
        List<AnnouncementImageEntity> announcementImageEntitiesMock = new EasyRandom()
                .objects(AnnouncementImageEntity.class, 3)
                .toList();
        Mockito.when(announcementImageRepository.findAllByAnnouncementId(announcementId)).thenReturn(announcementImageEntitiesMock);

        List<AnnouncementImageEntity> localized = announcementImageService.getAllByAnnouncementId(announcementId);

        assertEquals(3, localized.size());
    }

    @Test
    @DisplayName("Should deleted image announcement when id is valid")
    void delete() {
        Mockito.when(announcementImageRepository.findById(announcementImageEntityMock.getId())).thenReturn(Optional.of(announcementImageEntityMock));
        assertDoesNotThrow(() -> announcementImageService.delete(announcementImageEntityMock.getId()));
    }

}
