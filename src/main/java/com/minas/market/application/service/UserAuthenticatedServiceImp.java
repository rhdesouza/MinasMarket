package com.minas.market.application.service;

import com.minas.market.infrastructure.persistence.entity.security.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticatedServiceImp {

    private User getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        return authentication != null ? (User) authentication.getPrincipal() : null;
    }

    public User me() {
        return getUserAuthenticated();
    }
}
