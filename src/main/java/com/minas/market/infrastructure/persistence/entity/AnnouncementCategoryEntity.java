package com.minas.market.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "announcement_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementCategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "category_description", nullable = false)
    private String category;
    @Column(name = "date_disabled")
    private Date disabledDate;


}
