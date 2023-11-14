package com.minas.market.application.service;

import com.minas.market.domain.interfaces.AnnouncementCategoryService;
import com.minas.market.infrastructure.persistence.entity.AnnouncementCategoryEntity;
import com.minas.market.infrastructure.persistence.repository.AnnouncementCategoryRepository;
import com.minas.market.webapi.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Service
public class AnnouncementCategoryServiceImp implements AnnouncementCategoryService {

    private final AnnouncementCategoryRepository announcementCategoryRepository;

    public AnnouncementCategoryServiceImp(AnnouncementCategoryRepository announcementCategoryRepository) {
        this.announcementCategoryRepository = announcementCategoryRepository;
    }

    @Override
    public UUID createCategory(String category) {
        AnnouncementCategoryEntity entity = AnnouncementCategoryEntity.builder()
                .category(category)
                .build();

        return announcementCategoryRepository.save(entity).getId();
    }

    @Override
    public AnnouncementCategoryEntity getCategory(UUID id) {
        return announcementCategoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category not found"));

    }

    @Override
    public Collection<AnnouncementCategoryEntity> getAllCategories() {
        return announcementCategoryRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        AnnouncementCategoryEntity category = getCategory(id);
        category.setDisabledDate(new Date());

        announcementCategoryRepository.save(category);
    }
}
