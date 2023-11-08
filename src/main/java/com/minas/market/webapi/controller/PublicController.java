package com.minas.market.webapi.controller;

import com.minas.market.domain.interfaces.PublicService;
import com.minas.market.infrastructure.mapper.AnnouncementMapper;
import com.minas.market.infrastructure.persistence.entity.AnnouncementEntity;
import com.minas.market.webapi.contract.PublicAPI;
import com.minas.market.webapi.dto.Announcement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@RestController
public class PublicController implements PublicAPI {
    @Autowired
    private PublicService publicService;

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public ResponseEntity<List<Announcement>> getAnnouncements(String title) {
        List<AnnouncementEntity> announcementEntities = publicService.findAnnouncements(title);
        List<Announcement> announcementsDTO = announcementEntities.stream().map(announcementMapper::toDTO).toList();
        return ResponseEntity.ok(announcementsDTO);
    }
}
