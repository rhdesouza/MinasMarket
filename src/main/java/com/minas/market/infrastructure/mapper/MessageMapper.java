package com.minas.market.infrastructure.mapper;

import com.minas.market.infrastructure.persistence.entity.MessageEntity;
import com.minas.market.webapi.dto.Message;
import com.minas.market.webapi.dto.request.MessageRequest;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageEntity toEntity(Message dto);

    MessageEntity toEntity(MessageRequest messageRequest, UUID userId);

    Message toDTO(MessageEntity messageEntity);
}
