package com.minas.market.webapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRequest {
    @NotNull
    private UUID userId;
    @NotNull
    private AnnouncementCategoryRequest category;
    @NotEmpty
    private String description;
    @NotNull
    @DecimalMin(value = "1")
    private BigDecimal saleValue;

    //TODO: Retirar este campo, existe um CRUD correto para ele
    private List<AnnouncementImageRequest> images;
}
