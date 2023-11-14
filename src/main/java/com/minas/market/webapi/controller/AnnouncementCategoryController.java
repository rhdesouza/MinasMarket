package com.minas.market.webapi.controller;

import com.minas.market.domain.interfaces.AnnouncementCategoryService;
import com.minas.market.infrastructure.mapper.AnnouncementCategoryMapper;
import com.minas.market.webapi.contract.AnnouncementCategoryAPI;
import com.minas.market.webapi.dto.AnnouncementCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class AnnouncementCategoryController implements AnnouncementCategoryAPI {

    private final AnnouncementCategoryService announcementCategoryService;
    private final AnnouncementCategoryMapper announcementCategoryMapper;

    public AnnouncementCategoryController(AnnouncementCategoryService announcementCategoryService, AnnouncementCategoryMapper announcementCategoryMapper) {
        this.announcementCategoryService = announcementCategoryService;
        this.announcementCategoryMapper = announcementCategoryMapper;
    }

    @Override
    public ResponseEntity<UUID> create(String category) {
        UUID categoryId = announcementCategoryService.createCategory(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryId)
                .toUri();

        return ResponseEntity.created(uri).body(categoryId);
    }

    @Override
    public ResponseEntity<AnnouncementCategory> getOne(UUID categoryId) {
        AnnouncementCategory dto = announcementCategoryMapper.toDto(announcementCategoryService.getCategory(categoryId));
        return ResponseEntity.ok(dto);

    }

    @Override
    public ResponseEntity<List<AnnouncementCategory>> getAllCategories() {
        List<AnnouncementCategory> categories = announcementCategoryService.getAllCategories()
                .stream()
                .map(announcementCategoryMapper::toDto)
                .toList();
        return ResponseEntity.ok(categories);
    }

    @Override
    public void delete(UUID categoryId) {
        announcementCategoryService.delete(categoryId);
    }
}
