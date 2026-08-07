package com.example.iksystem.dto.protocol;

import com.example.iksystem.enums.model.constant.ProtocolStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolResponseDto {
    private UUID id;
    private UUID companyId;
    private String companyName;
    private UUID categoryId;
    private String categoryName;
    private String title;
    private Integer discountPercent;
    private String discountDetailText;
    private String specialConditions;
    private LocalDate beginDate;
    private LocalDate endDate;
    private ProtocolStatus status;
    private List<ProtocolAttachmentResponseDto> attachments;

}
