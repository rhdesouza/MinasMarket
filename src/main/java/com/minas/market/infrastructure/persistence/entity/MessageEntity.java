package com.minas.market.infrastructure.persistence.entity;

import com.minas.market.infrastructure.config.auditable.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id"),
        @Index(name = "announcement_id_idx", columnList = "announcement_id"),
})
public class MessageEntity extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "announcement_id")
    private UUID announcementId;

    @Column(name = "message")
    private String message;

    @Column(name = "read")
    private Boolean read;

    @Column(name = "deleted")
    private Boolean deleted;

}
