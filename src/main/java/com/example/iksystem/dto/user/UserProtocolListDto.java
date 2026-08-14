package com.example.iksystem.dto.user;

import com.example.iksystem.enums.model.constant.AttachmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Bu sınıf User Protocol List Dto nesnesini temsil eder.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProtocolListDto {
    private UUID id;
    private String title;
    private String companyName;
    private String categoryName;
    private Integer discountPercentage;
    private String logoUrl;
    private LocalDate beginDate;
    private LocalDate endDate;
    private Boolean protocolStatus;
    private String discountDetailsText;
    private String specialConditions;

    // 👇 Kullanıcının listede afişi/dosyayı görebilmesi için gerekli alanlar:
    private String fileUrl;
    private AttachmentType attachmentType;
}