package com.minas.market.infrastructure.persistence.entity.enums;

import com.minas.market.webapi.exception.BusinessRuleException;

public enum TypeUser {
    PJ, PF;

    public static TypeUser getEnum(String valor) {
        if (valor.equalsIgnoreCase(PJ.toString())) {
            return TypeUser.PJ;
        }
        if (valor.equalsIgnoreCase(PF.toString())) {
            return TypeUser.PF;
        }
        throw new BusinessRuleException("Type user is not exists");
    }
}
