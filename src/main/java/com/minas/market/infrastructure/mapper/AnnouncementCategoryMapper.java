package com.minas.market.infrastructure.mapper;

import com.minas.market.infrastructure.persistence.entity.AnnouncementCategoryEntity;
import com.minas.market.webapi.dto.AnnouncementCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnnouncementCategoryMapper {

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "category", source = "entity.category")
    @Mapping(target = "active", expression = "java(getIsActive(entity))")
    AnnouncementCategory toDto(AnnouncementCategoryEntity entity);


    default boolean getIsActive(AnnouncementCategoryEntity entity) {
        return entity.getDisabledDate() == null;
    }
}
