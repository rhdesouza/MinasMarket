package com.minas.market.infrastructure.persistence.entity.enums;

import com.minas.market.webapi.exception.BusinessRuleException;

public enum AnnouncementType {
    PUBLIC, PRIVATE;

    public static AnnouncementType getEnum(String valor) {
        if (valor.equalsIgnoreCase(PUBLIC.toString())) {
            return AnnouncementType.PUBLIC;
        }
        if (valor.equalsIgnoreCase(PRIVATE.toString())) {
            return AnnouncementType.PRIVATE;
        }
        throw new BusinessRuleException("Type announcement is not exists");
    }
}
