package com.minas.market.webapi.controller;

import com.minas.market.domain.interfaces.AnnouncementImageService;
import com.minas.market.infrastructure.mapper.AnnouncementImageMapper;
import com.minas.market.infrastructure.persistence.entity.AnnouncementImageEntity;
import com.minas.market.webapi.contract.AnnnouncementImageAPI;
import com.minas.market.webapi.dto.AnnouncementImage;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AnnouncementImageController implements AnnnouncementImageAPI {

    @Autowired
    AnnouncementImageService announcementImageService;

    @Autowired
    AnnouncementImageMapper announcementImageMapper;

    @Override
    public ResponseEntity<UUID> create(
            @NotNull UUID announcementId,
            @NotNull MultipartFile file
    ) {
        UUID announcementImageId = announcementImageService.create(announcementId, file).getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(announcementImageId)
                .toUri();

        return ResponseEntity.created(uri).body(announcementImageId);
    }

    @Override
    public ResponseEntity<AnnouncementImage> getOne(UUID id) {
        AnnouncementImageEntity announcementImageEntity = announcementImageService.findById(id);
        return ResponseEntity.ok(announcementImageMapper.toDto(announcementImageEntity));
    }

    @Override
    public ResponseEntity<List<AnnouncementImage>> getAllByAnnouncementId(UUID announcementId) {
        List<AnnouncementImageEntity> allByAnnouncementId = announcementImageService.getAllByAnnouncementId(announcementId);
        List<AnnouncementImage> collect = allByAnnouncementId.stream().map(announcementImageMapper::toDto).toList();
        return ResponseEntity.ok(collect);
    }

    @Override
    public void delete(UUID id) {
        announcementImageService.delete(id);
    }
}
