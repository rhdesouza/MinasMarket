package com.minas.market.unit.application.service;

import com.minas.market.application.service.AddressServiceImp;
import com.minas.market.application.service.security.UserServiceImp;
import com.minas.market.infrastructure.mapper.AddressMapper;
import com.minas.market.infrastructure.persistence.entity.AddressEntity;
import com.minas.market.infrastructure.persistence.entity.security.User;
import com.minas.market.infrastructure.persistence.repository.AddressRepository;
import com.minas.market.webapi.dto.request.AddressRequest;
import com.minas.market.webapi.exception.NotFoundException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AddressServiceImpTest {

    @Autowired
    private AddressServiceImp addressServiceImp;
    @Autowired
    private AddressMapper addressMapper;
    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private UserServiceImp userServiceImp;
    private UUID userId;
    UUID addressId;
    private AddressRequest addressRequest;

    @BeforeEach
    void onInit() {
        userId = UUID.fromString("cf06573e-6684-41b9-9c77-15718480a45f");
        addressId = UUID.randomUUID();
        addressRequest = new EasyRandom().nextObject(AddressRequest.class);
    }

    @Test
    @DisplayName("Should create address when data is correct")
    void create_onSuccess() {
        AddressEntity expected = addressMapper.toEntity(addressRequest, userId);
        Mockito.when(addressRepository.save(any())).thenReturn(expected);
        Mockito.when(userServiceImp.findUserById(userId)).thenReturn(Optional.of(new User()));
        AddressEntity created = addressServiceImp.create(userId, addressRequest);

        assertEquals(expected, created);
    }

    @Test
    @DisplayName("Should updated address when data is correct")
    void update_onSuccess() {
        AddressEntity expected = addressMapper.toEntity(addressRequest, userId);
        Mockito.when(addressRepository.save(any())).thenReturn(expected);
        Mockito.when(userServiceImp.findUserById(userId)).thenReturn(Optional.of(new User()));
        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.of(expected));
        AddressEntity created = addressServiceImp.update(userId, addressId, addressRequest);
        assertEquals(expected, created);
    }

    @Test
    @DisplayName("Should find address when exists")
    void findById_onSuccess() {
        AddressEntity expected = addressMapper.toEntity(addressRequest, userId);
        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.of(expected));
        AddressEntity localized = addressServiceImp.findById(addressId);

        assertEquals(expected, localized);
    }

    @Test
    @DisplayName("Should throw error when address is not found")
    void findById_onError() {
        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> addressServiceImp.findById(addressId));

        assertEquals("Address not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should find all address by user id")
    void findAllByUserId() {
        List<AddressEntity> locacaoEntitiesMock = new EasyRandom()
                .objects(AddressEntity.class, 3)
                .toList();
        Mockito.when(addressRepository.findAllByUserId(userId)).thenReturn(locacaoEntitiesMock);

        List<AddressEntity> localized = addressServiceImp.findAllByUserId(userId);

        assertEquals(3, localized.size());
    }

    @Test
    @DisplayName("Should deleted address when id is valid")
    void delete() {
        AddressEntity expected = addressMapper.toEntity(addressRequest, userId);
        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.of(expected));

        assertDoesNotThrow(() -> addressServiceImp.delete(addressId));
    }

}
