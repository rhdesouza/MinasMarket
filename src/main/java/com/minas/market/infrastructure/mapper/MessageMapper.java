package com.minas.market.infrastructure.mapper;

import com.minas.market.infrastructure.persistence.entity.MessageEntity;
import com.minas.market.webapi.dto.Message;
import com.minas.market.webapi.dto.request.MessageRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageEntity toEntity(Message dto);

    MessageEntity toEntity(MessageRequest messageRequest);

    Message toDTO(MessageEntity messageEntity);
}
