package com.minas.market.infrastructure.persistence.entity.security;


import com.minas.market.webapi.exception.BusinessRuleException;

/**
 * @author rhdesouza
 * ROLE
 * CDU
 * ACAO
 */
public class ConstRoles {

    ConstRoles(){
        throw new BusinessRuleException("class not implemented");
    }

    /**
     * Role Admin
     */
    public static final String ROLE_ADMIN_ADMIN = "ROLE_ADMIN_ADMIN";

    /**
     * Role Cliente
     */
    public static final String ROLE_PJ = "ROLE_PJ";
    public static final String ROLE_PJ_GET = "ROLE_PJ_GET";
    public static final String ROLE_PJ_POST = "ROLE_PJ_POST";
}
