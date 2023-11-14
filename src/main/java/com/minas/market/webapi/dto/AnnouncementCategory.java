package com.minas.market.webapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AnnouncementCategory {
    private UUID id;
    private String category;
    private boolean isActive;
}
