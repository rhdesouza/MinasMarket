package com.minas.market.unit.application.service;

import com.minas.market.application.service.AnnouncementCategoryServiceImp;
import com.minas.market.infrastructure.persistence.entity.AnnouncementCategoryEntity;
import com.minas.market.infrastructure.persistence.repository.AnnouncementCategoryRepository;
import com.minas.market.webapi.exception.NotFoundException;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.jeasy.random.FieldPredicates.named;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AnnouncementCategoryServiceImpTest {

    @Autowired
    private AnnouncementCategoryServiceImp announcementCategoryServiceImp;
    @MockBean
    private AnnouncementCategoryRepository announcementCategoryRepository;
    private AnnouncementCategoryEntity entityMock;

    @BeforeEach
    void onInit() {
        entityMock = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("disabledDate"), () -> null)
        ).nextObject(AnnouncementCategoryEntity.class);
    }

    @Test
    @DisplayName("Should create announcement category when data is correct")
    void create_onSuccess() {
        Mockito.when(announcementCategoryRepository.save(any())).thenReturn(entityMock);

        UUID categoryId = announcementCategoryServiceImp.createCategory("test");

        assertEquals(entityMock.getId(), categoryId);
    }

    @Test
    @DisplayName("Should find category announcement when exists")
    void getById_onSuccess() {
        Mockito.when(announcementCategoryRepository.findById(entityMock.getId())).thenReturn(Optional.of(entityMock));

        AnnouncementCategoryEntity category = announcementCategoryServiceImp.getCategory(entityMock.getId());

        assertEquals(entityMock.getId(), category.getId());
        assertEquals(entityMock.getCategory(), category.getCategory());
    }

    @Test
    @DisplayName("Should throw error when category announcement is not found")
    void findById_onError() {
        Mockito.when(announcementCategoryRepository.findById(entityMock.getId())).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> announcementCategoryServiceImp.getCategory(entityMock.getId()));

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should find all categories announcements by user id")
    void findAllByUserId() {
        List<AnnouncementCategoryEntity> categoriesEntitiesMock = new EasyRandom()
                .objects(AnnouncementCategoryEntity.class, 3)
                .toList();
        Mockito.when(announcementCategoryRepository.findAll()).thenReturn(categoriesEntitiesMock);

        Collection<AnnouncementCategoryEntity> localized = announcementCategoryServiceImp.getAllCategories();

        assertEquals(3, localized.size());
    }

    @Test
    @DisplayName("Should deleted announcement when id is valid")
    void delete() {
        Mockito.when(announcementCategoryRepository.findById(entityMock.getId())).thenReturn(Optional.of(entityMock));
        assertDoesNotThrow(() -> announcementCategoryServiceImp.delete(entityMock.getId()));
    }

}
