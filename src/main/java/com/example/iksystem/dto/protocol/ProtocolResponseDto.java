package com.example.iksystem.dto.protocol;

import com.example.iksystem.enums.model.constant.ProtocolStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Bu sınıf Protocol Response Dto nesnesini temsil eder.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProtocolResponseDto {

    private UUID id;
    private UUID companyId;
    private String companyName;
    private UUID categoryId;
    private String categoryName;
    private String title;
    private Integer discountPercentage;
    private String discountDetailText;
    private String specialConditions;
    private LocalDate beginDate;
    private LocalDate endDate;
    private ProtocolStatus protocolStatus;
    private List<ProtocolAttachmentResponseDto> attachments;
}