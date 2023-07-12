package com.minas.market.infrastructure.persistence.repository;

import com.minas.market.infrastructure.persistence.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {

    List<AddressEntity> findAllByUserId(UUID userId);

}
