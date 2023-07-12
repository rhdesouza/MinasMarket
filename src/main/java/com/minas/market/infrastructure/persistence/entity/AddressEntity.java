package com.minas.market.infrastructure.persistence.entity;

import com.minas.market.infrastructure.config.auditable.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Audited
public class AddressEntity extends Auditable<String> implements Serializable {

    @Id
    private UUID id;
    @NotNull
    @Column(name = "user_id")
    private UUID userId;
    @NotNull
    @Column(name = "zip_code")
    private String zipCode;
    @NotNull
    private String road;
    @NotNull
    private Integer number;
    @Column(name = "description_address")
    @Comment("Descrição do endereço")
    private String descriptionAddress;
    @Comment("Seleciona o endereço padrão, somente 1 por registro")
    private boolean isDefault;
    private boolean isActive;

    @PrePersist
    void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }

}
