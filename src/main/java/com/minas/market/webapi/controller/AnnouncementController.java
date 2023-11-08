package com.minas.market.webapi.controller;

import com.minas.market.domain.interfaces.AnnouncementService;
import com.minas.market.infrastructure.mapper.AnnouncementMapper;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.webapi.contract.AnnouncementAPI;
import com.minas.market.webapi.dto.Announcement;
import com.minas.market.webapi.dto.AnnouncementMessage;
import com.minas.market.webapi.dto.request.AnnouncementRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class AnnouncementController implements AnnouncementAPI {
    private final AnnouncementService announcementService;
    private final AnnouncementMapper announcementMapper;

    public AnnouncementController(AnnouncementService announcementService, AnnouncementMapper announcementMapper) {
        this.announcementService = announcementService;
        this.announcementMapper = announcementMapper;
    }

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
    public ResponseEntity<AnnouncementMessage> getOne(UUID announcementId) {
        AnnouncementMessage announcementMessage = announcementMapper.toDTOWithMessage(announcementService.findById(announcementId));
        return ResponseEntity.ok(announcementMessage);
    }

    @Override
    public ResponseEntity<List<Announcement>> getAllByUserId() {
        List<AnnouncementEntity> announcementEntities = announcementService.findAllByUserId();
        List<Announcement> announcementsDTO = announcementEntities.stream().map(announcementMapper::toDTO).toList();
        return ResponseEntity.ok(announcementsDTO);
    }

    @Override
    public void delete(UUID announcementId) {
        announcementService.delete(announcementId);
    }
}
