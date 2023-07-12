package com.minas.market.infrastructure.persistence.entity;

import com.minas.market.infrastructure.config.auditable.Auditable;
import com.minas.market.infrastructure.persistence.entity.enums.AnnouncementCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "announcement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Audited
public class AnnouncementEntity extends Auditable<String> implements Serializable {

    @Id
    private UUID id;
    @NotNull
    @Column(name = "user_id")
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private AnnouncementCategory category;
    @NotNull
    private String description;
    @Column(name = "sale_value")
    private BigDecimal saleValue;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "announcement_id", unique = true)
    @Audited(targetAuditMode = NOT_AUDITED)
    private List<AnnouncementImageEntity> images;
    private boolean isSold;
    private boolean isActive;


    @PrePersist
    void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }

}
