package com.minas.market.webapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRequest {
    @NotNull
    private AnnouncementCategoryRequest category;
    @NotNull
    AnnouncementTypeRequest type;
    @NotEmpty
    private String description;
    @NotNull
    @DecimalMin(value = "1")
    private BigDecimal saleValue;
}
