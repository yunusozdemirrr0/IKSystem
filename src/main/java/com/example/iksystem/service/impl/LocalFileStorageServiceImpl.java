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
/**
 * Bu sınıf Local File Storage Service Impl nesnesini temsil eder.
 */


@Service
@Slf4j
public class LocalFileStorageServiceImpl implements FileStorageService {
    private final Path fileStorageLocation;

    // İzin verilen dosya uzantıları listesi
    List<String> allowedExtensions = List.of(".jpg", ".jpeg", ".png", ".gif", ".pdf", ".docx", ".xlsx");

    //Bu sınıfın yapıcı metodu, dosya yükleme dizinini alır ve bu dizini oluşturur. Eğer dizin oluşturulamazsa bir RuntimeException fırlatır.
    LocalFileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();// Dosya yükleme dizinini alır ve normalize eder.
        try {// Dosya yükleme dizinini oluşturur. Eğer dizin zaten varsa, hiçbir şey yapmaz.
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {// Eğer dizin oluşturulamazsa bir RuntimeException fırlatır.
            throw new RuntimeException("Could not create upload directory", ex);
        }

    }


    @Override
    // Bu metot, yüklenen dosyayı alır ve dosya sistemine kaydeder. Dosya adı benzersiz olacak şekilde UUID ile değiştirilir. Eğer dosya boşsa veya geçersiz bir uzantıya sahipse bir IllegalArgumentException fırlatır.
    public String storeFile(MultipartFile file) {
       if (file==null||file.isEmpty()) { // Dosya boşsa veya null ise IllegalArgumentException fırlatır.
           throw new IllegalArgumentException("File is empty");

       }
       String originalFilename = StringUtils.cleanPath(file.getOriginalFilename()); // Dosya adını temizler ve normalize eder.
       if (originalFilename.contains("..")) { // Dosya adı geçersizse IllegalArgumentException fırlatır.
           throw new IllegalArgumentException("Invalid file path: " + originalFilename);
       }
       int lastIndex=originalFilename.lastIndexOf('.'); // Dosya adının son noktasını bulur ve uzantıyı alır. Eğer uzantı yoksa IllegalArgumentException fırlatır.
       if (lastIndex==-1) { // Dosya uzantısı yoksa IllegalArgumentException fırlatır.
           throw new IllegalArgumentException("Invalid file extension: " + originalFilename);
       }

       String extension = originalFilename.substring(lastIndex).toLowerCase(); // Dosya uzantısını küçük harfe çevirir ve izin verilen uzantılar listesinde olup olmadığını kontrol eder. Eğer izin verilen uzantılar listesinde değilse IllegalArgumentException fırlatır.
       if (!allowedExtensions.contains(extension)) {
           throw new IllegalArgumentException("Invalid file extension: " + extension);
       }

       String uniqueFileName = UUID.randomUUID() + extension;// Dosya adını benzersiz yapmak için UUID ile değiştirir ve uzantıyı ekler.
       Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);

       // Dosyayı disk üzerine yazmak için InputStream kullanır ve dosya zaten varsa üzerine yazar. Eğer dosya yazılamazsa bir RuntimeException fırlatır.
       try(InputStream inputStream = file.getInputStream()) {
           Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

           return uniqueFileName;
       } catch (IOException ex) {// Dosya yazılamazsa bir RuntimeException fırlatır.
           log.error("File couldn't write to disk:{}", uniqueFileName, ex);
           throw new RuntimeException("File couldn't write to disk" +originalFilename+"Please try again!", ex);
       }


    }

    @Override
    // Bu metot, dosya adını alır ve dosya sisteminden dosyayı yükler. Eğer dosya bulunamazsa veya okunamazsa bir RuntimeException fırlatır.
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
    // Bu metot, dosya adını alır ve dosya sisteminden dosyayı siler. Eğer dosya bulunamazsa veya silinemezse bir RuntimeException fırlatır.
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
