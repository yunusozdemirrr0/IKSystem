package com.example.iksystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * Bu sınıf File Storage Properties nesnesini temsil eder.
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "file") // application.properties dosyasındaki "file" önekine sahip yapılandırma özelliklerini bağlar.
public class FileStorageProperties {
   // Bu alan, dosya yükleme dizinini temsil eder.
    private String uploadDir;
}
