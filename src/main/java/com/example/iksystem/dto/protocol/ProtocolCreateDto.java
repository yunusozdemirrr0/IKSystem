package com.example.iksystem.dto.protocol;

import com.example.iksystem.enums.model.constant.ProtocolStatus;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;
/**
 * Bu sınıf Protocol Create Dto nesnesini temsil eder.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProtocolCreateDto {
    @NotNull
    @Valid
    private List<ProtocolAttachmentDto> attachments;



    @NotNull(message = "companyId cannot be null")
    private UUID companyId;
    @NotNull(message = "categoryId cannot be null")
    private UUID categoryId;
    @NotBlank(message = "title cannot be blank")
    private String title;
    @Min(value = 0, message = "discountPercent must be greater than or equal to 0")
    @Max(value = 100, message = "discountPercent must be less than or equal to 100")
    private Integer discountPercentage;
    @NotBlank
    private String discountDetailText;
    @NotNull
    @FutureOrPresent
    private LocalDate beginDate;
    @NotNull
    @FutureOrPresent
    private LocalDate endDate;
    @NotNull
    private ProtocolStatus protocolStatus;
    private String specialConditions;


}
