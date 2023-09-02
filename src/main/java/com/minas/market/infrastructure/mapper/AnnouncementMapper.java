package com.minas.market.infrastructure.mapper;

import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.infrastructure.persistence.entity.enums.AnnouncementCategory;
import com.minas.market.webapi.dto.Announcement;
import com.minas.market.webapi.dto.AnnouncementMessage;
import com.minas.market.webapi.dto.request.AnnouncementCategoryRequest;
import com.minas.market.webapi.dto.request.AnnouncementRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {

    Announcement toDTO(AnnouncementEntity announcementEntity);

    AnnouncementMessage toDTOWithMessage(AnnouncementEntity announcementEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isSold", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "category", expression = "java(parseToAnnouncementCategory(announcementRequest.getCategory()))")
    @Mapping(target = "userId", source = "idAuthenticatedUser")
    AnnouncementEntity toEntity(AnnouncementRequest announcementRequest, UUID idAuthenticatedUser);

    default AnnouncementCategory parseToAnnouncementCategory(AnnouncementCategoryRequest announcementCategoryRequest) {
        return AnnouncementCategory.getEnum(announcementCategoryRequest.name());
    }
}
