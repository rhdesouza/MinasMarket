package com.minas.market.infrastructure.config.auditable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "customAuditProvider")
public class AuditableConfig {
    @Bean
    public AuditorAware<String> customAuditProvider() {
        return new AuditorAwareImpl();
    }
}
