package com.example.iksystem.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProtocolListDto {
    private UUID id;
    private String title;
    private String companyName;
    private String categoryName;
    private Integer discountPercent;
    private String logoUrl;
    private LocalDate endDate;
}
