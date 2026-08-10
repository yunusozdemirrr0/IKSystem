package com.example.iksystem.service.impl;

import com.example.iksystem.config.FileStorageProperties;
import com.example.iksystem.service.FileStorageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class LocalFileStorageServiceImpl implements FileStorageService {
    private final Path fileStorageLocation;
    List<String> allowedExtensions = List.of(".jpg", ".jpeg", ".png", ".gif", ".pdf", ".docx", ".xlsx");


    LocalFileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create upload directory", ex);
        }

    }


    @Override
    public String storeFile(MultipartFile file) {
       if (file==null||file.isEmpty()) {
           throw new IllegalArgumentException("File is empty");

       }
       String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
       if (originalFilename.contains("..")) {
           throw new IllegalArgumentException("Invalid file path: " + originalFilename);
       }
       int lastIndex=originalFilename.lastIndexOf('.');
       if (lastIndex==-1) {
           throw new IllegalArgumentException("Invalid file extension: " + originalFilename);
       }

       String extension = originalFilename.substring(lastIndex).toLowerCase();
       if (!allowedExtensions.contains(extension)) {
           throw new IllegalArgumentException("Invalid file extension: " + extension);
       }

       String uniqueFileName = UUID.randomUUID() + extension;
       Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);

       try(InputStream inputStream = file.getInputStream()) {
           Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

           return uniqueFileName;
       } catch (IOException ex) {
           log.error("File couldn't write to disk:{}", uniqueFileName, ex);
           throw new RuntimeException("File couldn't write to disk" +originalFilename+"Please try again!", ex);
       }


    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path file = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found or not readable: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found : " + fileName, ex);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            Path file = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(file);
        } catch (IOException ex) {
            log.error("Could not delete file:{} " , fileName, ex);
            throw new RuntimeException("File not found or not readable: " + fileName, ex);
        }
    }


}
