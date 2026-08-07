package com.example.iksystem.dto.protocol;

import com.example.iksystem.enums.model.constant.AttachmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolAttachmentDto {
    @NotBlank(message = "fileUrl cannot be blank") @URL(message = "fileUrl must be a valid URL")
    private String fileUrl;
    @NotNull(message = "attachmentType cannot be null")

    private AttachmentType attachmentType;
    private Boolean showPersonel;

















}
