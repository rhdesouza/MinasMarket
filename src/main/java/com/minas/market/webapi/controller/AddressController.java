package com.minas.market.webapi.controller;

import com.minas.market.domain.interfaces.AddressService;
import com.minas.market.infrastructure.mapper.AddressMapper;
import com.minas.market.infrastructure.persistence.entity.AddressEntity;
import com.minas.market.webapi.contract.AddressAPI;
import com.minas.market.webapi.dto.Address;
import com.minas.market.webapi.dto.request.AddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class AddressController implements AddressAPI {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public ResponseEntity<UUID> create(UUID userId, AddressRequest addressCreate) {
        UUID addressId = addressService.create(userId, addressCreate).getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(addressId)
                .toUri();

        return ResponseEntity.created(uri).body(addressId);
    }

    @Override
    public ResponseEntity<UUID> update(UUID userId, UUID addressId, AddressRequest addressUpdate) {
        return ResponseEntity.ok(addressService.update(userId, addressId, addressUpdate).getId());
    }

    @Override
    public ResponseEntity<Address> getOne(UUID id) {
        Address address = addressMapper.toDTO(addressService.findById(id));
        return ResponseEntity.ok(address);
    }

    @Override
    public ResponseEntity<List<Address>> getAll(UUID userId) {
        List<AddressEntity> addressEntities = addressService.findAllByUserId(userId);
        List<Address> addressDTO = addressEntities.stream().map(addressMapper::toDTO).toList();
        return ResponseEntity.ok(addressDTO);
    }

    @Override
    public void delete(UUID id) {
        addressService.delete(id);
    }
}
