package com.example.iksystem.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Bu sınıf File Upload Response Dto nesnesini temsil eder.
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Bu sınıf, dosya yükleme yanıtı için kullanılan veri transfer nesnesini temsil eder.
public class FileUploadResponseDto {

    private UUID fileId;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
