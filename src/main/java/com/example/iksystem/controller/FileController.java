package com.example.iksystem.controller;

import com.example.iksystem.dto.file.FileUploadResponseDto;
import com.example.iksystem.service.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
/**
 * Bu sınıf File Controller nesnesini temsil eder.
 */

@RestController
@RequestMapping("/api/v1/admin/files")
@RequiredArgsConstructor
@Tag(name = "File Management", description = "APIs for managing files") // Swagger/OpenAPI tag for documentation
public class FileController {
    private final FileStorageService fileStorageService;

   /// Bu metod, dosya yüklemek için HTTP POST isteğini işler.
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // Dosya yükleme işlemi için HTTP POST isteğini işler ve çok parçalı form verisi (multipart/form-data) olarak dosya alır.
    public ResponseEntity<FileUploadResponseDto> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/admin/files/download/").path(fileName).toUriString();
        FileUploadResponseDto responseDto = FileUploadResponseDto.builder() // FileUploadResponseDto nesnesini oluşturur ve yanıt olarak döndürür.
                .fileName(fileName)
                .fileDownloadUri(fileDownloadUri)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); }

    /// Bu metod, dosya indirmek için HTTP GET isteğini işler.
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null; /// Dosyanın içerik türünü belirlemek için kullanılır.
        /// Dosyanın içerik türünü belirlemek için kullanılır. Eğer içerik türü belirlenemezse, varsayılan olarak "application/octet-stream" kullanılır.
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            /// İçerik türü belirlenemezse, varsayılan olarak "application/octet-stream" kullanılır.
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"") /// Dosya indirme yanıt başlığı ayarlanır.
                .body(resource); /// Dosya kaynağı yanıt gövdesine eklenir.
    }

    /// Bu metod, dosya silmek için HTTP DELETE isteğini işler.
    @DeleteMapping("/delete/{fileName:.+}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileName) {
        fileStorageService.deleteFile(fileName);
        return ResponseEntity.noContent().build();
    }





}
