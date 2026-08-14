package com.example.iksystem.dto.protocol;

import com.example.iksystem.enums.model.constant.AttachmentType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import lombok.*;
/**
 * Bu sınıf Protocol Attachment Dto nesnesini temsil eder.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolAttachmentDto {
   private String fileName;
    @NotBlank(message = "fileUrl cannot be blank") @URL(message = "fileUrl must be a valid URL")
    private String filePathUrl;
    @NotNull(message = "attachmentType cannot be null")
    @Column(name = "file_type")
    private AttachmentType attachmentType;
    @Column(name = "show_personel")
    private Boolean showPersonel;


















}
