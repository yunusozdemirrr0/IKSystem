package com.example.iksystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "protocols")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProtocolsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private Integer discountPercentage;
    @Lob
    @Column(name = "discount_info")
    private String discountDetailsText;
    private String specialConditions;
    private LocalDate beginDate;
    private LocalDate endDate;
    private boolean protocolStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id",nullable = false)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private CategoriesEntity category;

    @OneToMany(mappedBy = "protocol", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProtocolAttachmentEntity> protocolFiles=new ArrayList<>();



}
