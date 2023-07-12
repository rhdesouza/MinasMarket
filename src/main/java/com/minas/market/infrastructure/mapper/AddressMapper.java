package com.minas.market.infrastructure.mapper;

import com.minas.market.webapi.dto.Address;
import com.minas.market.infrastructure.persistence.entity.AddressEntity;
import com.minas.market.webapi.dto.request.AddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    AddressEntity toEntity(AddressRequest addressCreate, UUID userId);

    Address toDTO(AddressEntity entity);

}
