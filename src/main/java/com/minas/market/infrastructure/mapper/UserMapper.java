package com.minas.market.infrastructure.mapper;

import com.minas.market.infrastructure.persistence.entity.security.Role;
import com.minas.market.webapi.dto.request.UserDTO;
import com.minas.market.infrastructure.persistence.entity.AddressEntity;
import com.minas.market.infrastructure.persistence.entity.security.User;
import com.minas.market.webapi.dto.request.AddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "roles", source = "rolesMapper")
    @Mapping(target = "type", expression = "java(com.minas.market.infrastructure.persistence.entity.enums.TypeUser.getEnum(userDTO.getTypeUser()))")
    @Mapping(target = "address", expression = "java(List.of(toAddressEntity(request)))")
    User toEntity(UserDTO userDTO, List<Role> rolesMapper, AddressRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    AddressEntity toAddressEntity(AddressRequest request);

}
