package com.example.iksystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
/**
 * Bu sınıf Categories Entity nesnesini temsil eder.
 */
@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "category_name", nullable = false)
    private String categoryName;
    private String icon;
    @Column(name = "status", nullable = false)
    @NotNull(message = "isActive cannot be null")
    private Boolean isActive;


}
