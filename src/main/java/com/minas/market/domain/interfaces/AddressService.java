package com.minas.market.domain.interfaces;

import com.minas.market.infrastructure.persistence.entity.AddressEntity;
import com.minas.market.webapi.dto.request.AddressRequest;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    AddressEntity create(UUID userId, AddressRequest addressCreate);

    AddressEntity update(UUID userId, UUID addressId, AddressRequest addressUpdate);

    AddressEntity findById(UUID id);

    List<AddressEntity> findAllByUserId(UUID userId);

    void delete(UUID id);
}
