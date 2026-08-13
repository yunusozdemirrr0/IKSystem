package com.example.iksystem.dto.protocol;

import com.example.iksystem.enums.model.constant.AttachmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Bu sınıf Protocol Attachment Response Dto nesnesini temsil eder.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolAttachmentResponseDto {

    private UUID id;
    private String fileUrl;
    private String fileName;
    private AttachmentType attachmentType;
    private Boolean showPersonel;
}