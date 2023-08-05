package com.minas.market.webapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private UUID id;
    private UUID userId;
    private UUID announcementId;
    private String message;
    private Boolean read;
    private Boolean deleted;
}
