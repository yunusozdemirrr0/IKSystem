package com.example.iksystem.dto.user;

import com.example.iksystem.dto.protocol.ProtocolAttachmentResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProtocolDetailDto {

    private UUID id;
    private String title;
    private String companyName;
    private String categoryName;
    private String logoUrl;
    private String discountDetailsText;
    private String specialConditions;
    private String telephone;
    private String email;
    private String address;
    private String mapUrl;
    private LocalDate beginDate;
    private LocalDate endDate;

    // Hem doğrudan string URL'ler hem de dosya detayları için:
    private List<String> fileDownloadUrls;
    private List<ProtocolAttachmentResponseDto> attachments;
}