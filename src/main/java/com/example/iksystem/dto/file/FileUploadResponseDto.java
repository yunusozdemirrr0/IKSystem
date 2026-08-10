package com.example.iksystem.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadResponseDto {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
