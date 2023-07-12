package com.minas.market.webapi.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class Address {
    private UUID id;
    private UUID userId;
    private String zipCode;
    private String road;
    private Integer number;
    private String descriptionAddress;
    private boolean isDefault;
    private boolean isActive;
}
