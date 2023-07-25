package com.minas.market.webapi.dto;

import java.util.UUID;

public class Message {
    private UUID id;
    private UUID userId;
    private UUID announcementId;
    private String message;
    private Boolean read;
}
