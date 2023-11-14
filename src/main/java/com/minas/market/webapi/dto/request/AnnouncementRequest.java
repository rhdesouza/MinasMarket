package com.minas.market.webapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRequest {
    @NotNull
    private String title;
    @NotNull
    private UUID categoryId;
    @NotNull
    AnnouncementTypeRequest type;
    @NotEmpty
    private String description;
    @NotNull
    @DecimalMin(value = "1")
    private BigDecimal saleValue;
}
