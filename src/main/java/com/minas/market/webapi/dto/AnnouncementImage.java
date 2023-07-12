package com.minas.market.webapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AnnouncementImage {
    private UUID id;
    private UUID announcementId;
    private String title;
}
