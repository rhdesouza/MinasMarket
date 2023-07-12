package com.minas.market.infrastructure.persistence.entity.enums;

import com.minas.market.webapi.exception.BusinessRuleException;

public enum AnnouncementCategory {

    AUTOMOTIVE, TECH, OTHERS;

    public static AnnouncementCategory getEnum(String valor) {
        if (valor.equalsIgnoreCase(AUTOMOTIVE.toString())) {
            return AnnouncementCategory.AUTOMOTIVE;
        }
        if (valor.equalsIgnoreCase(TECH.toString())) {
            return AnnouncementCategory.TECH;
        }
        if (valor.equalsIgnoreCase(OTHERS.toString())) {
            return AnnouncementCategory.OTHERS;
        }
        throw new BusinessRuleException("Category is not exists");
    }

}
