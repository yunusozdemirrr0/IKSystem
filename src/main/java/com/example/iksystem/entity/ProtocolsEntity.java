package com.example.iksystem.entity;

import com.example.iksystem.enums.model.constant.ProtocolStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
/**
 * Bu sınıf Protocols Entity nesnesini temsil eder.
 */

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
    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @Column(name = "discount_info", columnDefinition = "TEXT")
    private String discountDetailsText;
    @Column(name = "special_conditions", columnDefinition = "TEXT")
    private String specialConditions;
    @Column(name = "begin_date")
    private LocalDate beginDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "protocol_status")
    private ProtocolStatus protocolStatus;

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
