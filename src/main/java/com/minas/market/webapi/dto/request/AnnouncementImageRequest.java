package com.minas.market.webapi.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementImageRequest {
    @NotNull
    private UUID announcementId;
    @NotEmpty
    private String title;
    @NotNull
    private MultipartFile image;

}
