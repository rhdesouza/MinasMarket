package com.minas.market.infrastructure.config.auditable;

import com.minas.market.infrastructure.persistence.entity.security.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String ADMIN = "ADMIN";
    private static final String ANNO = "anonymousUser";

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal;

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of(ADMIN);
        } else if (authentication.getPrincipal() == ANNO) {
            return Optional.of(ANNO);
        } else {
            principal = (User) authentication.getPrincipal();
        }

        return Optional.ofNullable(principal.getId().toString());
    }
}
