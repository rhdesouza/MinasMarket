package com.minas.market.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "announcement_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementImageEntity {

    @Id
    private UUID id;
    @NotNull
    @Column(name = "announcement_id")
    private UUID announcementId;
    private String title;
    @Lob
    private byte[] image;

}
