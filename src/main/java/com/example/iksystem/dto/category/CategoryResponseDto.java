package com.example.iksystem.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
/**
 * Bu sınıf Category Response Dto nesnesini temsil eder.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Bu sınıf, kategori yanıt verilerini temsil eden bir DTO (Data Transfer Object) sınıfıdır.
public class CategoryResponseDto {
    private UUID id;
    private String categoryName;
    private String icon;
    private Boolean isActive;
}
