package com.minas.market.application.service;

import com.minas.market.domain.interfaces.AnnouncementImageService;
import com.minas.market.domain.interfaces.AnnouncementService;
import com.minas.market.infrastructure.mapper.AnnouncementImageMapper;
import com.minas.market.infrastructure.persistence.entity.AnnouncementImageEntity;
import com.minas.market.infrastructure.persistence.repository.AnnouncementImageRepository;
import com.minas.market.webapi.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class AnnouncementImageServiceImp implements AnnouncementImageService {

    @Autowired
    private AnnouncementImageMapper announcementImageMapper;
    @Autowired
    private AnnouncementImageRepository announcementImageRepository;
    @Autowired
    private AnnouncementService announcementService;

    @Override
    @Transactional
    public AnnouncementImageEntity create(UUID announcementId, MultipartFile file) {
        announcementService.findById(announcementId);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            throw new MultipartException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        try {
            AnnouncementImageEntity announcementImageEntity = new AnnouncementImageEntity();
            announcementImageEntity.setFileName(fileName);
            announcementImageEntity.setFileType(file.getContentType());
            announcementImageEntity.setSize(file.getSize());
            announcementImageEntity.setAnnouncementId(announcementId);
            announcementImageEntity.setData(file.getBytes());
            return announcementImageRepository.save(announcementImageEntity);
        } catch (MultipartException | IOException ex) {
            log.error(ex.getMessage());
            throw new MultipartException(ex.getMessage());
        }
    }

    @Override
    public AnnouncementImageEntity findById(UUID id) {
        return announcementImageRepository.findById(id).orElseThrow(() -> new NotFoundException("Image announcement not found"));
    }

    @Override
    @Transactional
    public List<AnnouncementImageEntity> getAllByAnnouncementId(UUID announcementId) {
        return announcementImageRepository.findAllByAnnouncementId(announcementId);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        announcementImageRepository.delete(findById(id));
    }
}
