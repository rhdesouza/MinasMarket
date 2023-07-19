package com.minas.market.infrastructure.mapper;

import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.webapi.dto.Announcement;
import com.minas.market.webapi.dto.request.AnnouncementRequest;
import com.minas.market.infrastructure.persistence.entity.enums.AnnouncementCategory;
import com.minas.market.webapi.dto.request.AnnouncementCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {

    Announcement toDTO(AnnouncementEntity announcementEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isSold", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "category", expression = "java(parseToAnnouncementCategory(announcementRequest.getCategory()))")
    AnnouncementEntity toEntity(AnnouncementRequest announcementRequest);

    default AnnouncementCategory parseToAnnouncementCategory(AnnouncementCategoryRequest announcementCategoryRequest){
        return AnnouncementCategory.getEnum(announcementCategoryRequest.name());
    }
}
