package com.minas.market.webapi.dto.request;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementImageRequest {
    private String title;
    @Lob
    private byte[] image;
}
