package com.minas.market.webapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class Announcement {
    private UUID id;
    private UUID userId;
    private String category;
    private String description;
    private BigDecimal saleValue;
    private List<AnnouncementImage> images;
    private boolean isSold;
    private boolean isActive;
}
