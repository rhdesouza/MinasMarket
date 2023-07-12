package com.minas.market.application.service;

import com.minas.market.domain.interfaces.AddressService;
import com.minas.market.infrastructure.mapper.AddressMapper;
import com.minas.market.webapi.exception.NotFoundException;
import com.minas.market.application.service.security.UserServiceImp;
import com.minas.market.infrastructure.persistence.entity.AddressEntity;
import com.minas.market.infrastructure.persistence.repository.AddressRepository;
import com.minas.market.webapi.dto.request.AddressRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressServiceImp implements AddressService {

    private AddressRepository addressRepository;
    private UserServiceImp userServiceImp;
    private AddressMapper addressMapper;

    @Transactional
    public AddressEntity create(UUID userId, AddressRequest addressCreate) {
        this.verifyUserId(userId);
        AddressEntity entity = addressMapper.toEntity(addressCreate, userId);
        entity.setDefault(false);
        entity.setActive(true);
        return addressRepository.save(entity);
    }

    @Transactional
    public AddressEntity update(UUID userId, UUID addressId, AddressRequest addressUpdate) {
        this.verifyUserId(userId);
        AddressEntity addressFound = findById(addressId);
        addressFound.setZipCode(addressUpdate.getZipCode());
        addressFound.setRoad(addressUpdate.getRoad());
        addressFound.setNumber(addressUpdate.getNumber());
        addressFound.setDescriptionAddress(addressUpdate.getDescriptionAddress());
        addressFound.setDefault(addressUpdate.isDefault());
        return addressRepository.save(addressFound);
    }

    private void verifyUserId(UUID userId) {
        if (userServiceImp.findUserById(userId).isEmpty()) {
            throw new NotFoundException("User not found");
        }
    }

    public AddressEntity findById(UUID id) {
        return addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address not found"));
    }

    public List<AddressEntity> findAllByUserId(UUID userId) {
        return addressRepository.findAllByUserId(userId);
    }

    @Transactional
    public void delete(UUID id) {
        AddressEntity address = findById(id);
        address.setActive(false);
        addressRepository.save(address);
    }
}
