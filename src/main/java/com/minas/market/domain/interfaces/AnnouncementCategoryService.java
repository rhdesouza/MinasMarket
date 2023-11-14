package com.minas.market.domain.interfaces;

import com.minas.market.infrastructure.persistence.entity.AnnouncementCategoryEntity;

import java.util.Collection;
import java.util.UUID;

public interface AnnouncementCategoryService {

    UUID createCategory(String category);

    AnnouncementCategoryEntity getCategory(UUID id);

    Collection<AnnouncementCategoryEntity> getAllCategories();

    void delete(UUID id);


}
