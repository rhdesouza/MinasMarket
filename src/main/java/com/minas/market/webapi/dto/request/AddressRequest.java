package com.minas.market.webapi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    @NotNull(message = "ZipCode not null")
    private String zipCode;
    @NotNull(message = "Road not null")
    private String road;
    @Min(1)
    private Integer number;
    private String descriptionAddress;
    private boolean isDefault;

}
