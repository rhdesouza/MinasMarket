package com.minas.market.webapi.controller;

import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.webapi.contract.AnnouncementAPI;
import com.minas.market.webapi.dto.Announcement;
import com.minas.market.webapi.dto.request.AnnouncementRequest;
import com.minas.market.domain.interfaces.AnnouncementService;
import com.minas.market.infrastructure.mapper.AnnouncementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AnnouncementController implements AnnouncementAPI {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    AnnouncementMapper announcementMapper;

    @Override
    public ResponseEntity<UUID> create(AnnouncementRequest announcementRequest) {
        UUID announcementId = announcementService.create(announcementRequest).getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(announcementId)
                .toUri();

        return ResponseEntity.created(uri).body(announcementId);
    }

    @Override
    public ResponseEntity<UUID> update(UUID announcementId, AnnouncementRequest announcementRequest) {
        return ResponseEntity.ok(announcementService.update(announcementId, announcementRequest).getId());
    }

    @Override
    public ResponseEntity<Announcement> getOne(UUID announcementId) {
        Announcement announcement = announcementMapper.toDTO(announcementService.findById(announcementId));
        return ResponseEntity.ok(announcement);
    }

    @Override
    public ResponseEntity<List<Announcement>> getAllByUserId(UUID userId) {
        List<AnnouncementEntity> announcementEntities = announcementService.findAllByUserId(userId);
        List<Announcement> announcementsDTO = announcementEntities.stream().map(announcementMapper::toDTO).toList();
        return ResponseEntity.ok(announcementsDTO);
    }

    @Override
    public void delete(UUID announcementId) {
        announcementService.delete(announcementId);
    }
}
