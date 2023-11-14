package com.minas.market.infrastructure.persistence.entity;

import com.minas.market.infrastructure.config.auditable.Auditable;
import com.minas.market.infrastructure.persistence.entity.enums.AnnouncementType;
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
@Table(name = "announcement", indexes = @Index(columnList = "title"))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Audited
public class AnnouncementEntity extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @Audited(targetAuditMode = NOT_AUDITED)
    private AnnouncementCategoryEntity announcementCategory;

    @Column(nullable = false)
    private AnnouncementType type;

    @NotNull
    private String description;

    @Column(name = "sale_value")
    private BigDecimal saleValue;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "announcement_id", unique = true)
    @Audited(targetAuditMode = NOT_AUDITED)
    private List<AnnouncementImageEntity> images;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id")
    @Audited(targetAuditMode = NOT_AUDITED)
    private List<MessageEntity> messages;

    private boolean isSold;

    private boolean isActive;

}
