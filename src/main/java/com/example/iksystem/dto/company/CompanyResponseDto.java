package com.example.iksystem.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Bu sınıf Company Response Dto nesnesini temsil eder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponseDto {

    private UUID id;
    private String companyName;
    private String address;
    private String email;

    // JSON çıktısında isteğe bağlı olarak 'phoneNumber' adı ile dönmesini sağlar
    @JsonProperty("phoneNumber")
    private String telephone;

    private String logoUrl;
    private Double latitude;
    private Double longitude;
    private Boolean isActive;
}