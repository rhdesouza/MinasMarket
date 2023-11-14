package com.minas.market.infrastructure.persistence.repository;

import com.minas.market.infrastructure.persistence.entity.AnnouncementCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnnouncementCategoryRepository extends JpaRepository<AnnouncementCategoryEntity, UUID> {

}
