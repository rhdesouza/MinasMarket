package com.minas.market.infrastructure.mapper;

import com.minas.market.infrastructure.persistence.entity.AnnouncementImageEntity;
import com.minas.market.webapi.dto.AnnouncementImage;
import com.minas.market.webapi.dto.request.AnnouncementImageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnnouncementImageMapper {
    @Mapping(target = "id", ignore = true)
    AnnouncementImageEntity toEntity(AnnouncementImageRequest announcementImageRequest);

    AnnouncementImage toDto(AnnouncementImageEntity announcementImageEntity);
}
