package com.example.iksystem.dto.protocol;

import com.example.iksystem.enums.model.constant.AttachmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

/**
 * Bu sınıf Protocol Attachment Dto nesnesini temsil eder.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolAttachmentDto {

 private String fileName;

 @NotBlank(message = "fileUrl cannot be blank")
 @URL(message = "fileUrl must be a valid URL")
 private String filePathUrl;

 @NotNull(message = "attachmentType cannot be null")
 private AttachmentType attachmentType;

 private Boolean showPersonel;
}