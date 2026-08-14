package com.example.iksystem.dto.protocol;

import com.example.iksystem.enums.model.constant.ProtocolStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Bu sınıf Protocol Create Dto nesnesini temsil eder.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolCreateDto {

    @NotNull(message = "companyId cannot be null")
    private UUID companyId;

    @NotNull(message = "categoryId cannot be null")
    private UUID categoryId;

    @NotBlank(message = "title cannot be blank")
    private String title;

    @Min(value = 0, message = "discountPercent must be greater than or equal to 0")
    @Max(value = 100, message = "discountPercent must be less than or equal to 100")
    private Integer discountPercentage;

    @NotBlank(message = "discountDetailText cannot be blank")
    private String discountDetailText;

    private String specialConditions;

    @NotNull(message = "beginDate cannot be null")
    @FutureOrPresent(message = "beginDate must be today or in the future")
    private LocalDate beginDate;

    @NotNull(message = "endDate cannot be null")
    @FutureOrPresent(message = "endDate must be today or in the future")
    private LocalDate endDate;

    private ProtocolStatus protocolStatus;

    @Valid
    private List<ProtocolAttachmentDto> attachments;
}