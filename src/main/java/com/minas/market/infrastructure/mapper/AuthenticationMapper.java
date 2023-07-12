package com.minas.market.infrastructure.mapper;

import com.minas.market.webapi.dto.AuthenticationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    @Mapping(target = "accessToken", source = "jwtToken")
    @Mapping(target = "refreshToken", source = "refreshToken")
    AuthenticationResponse toAuthenticationResponse(String jwtToken, String refreshToken);

}
