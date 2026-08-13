package com.example.iksystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;
/**
 * Bu sınıf Multipart Jackson Config nesnesini temsil eder.
 */

@Configuration
public class MultipartJacksonConfig {
    // Bu metod, Multipart Jackson yapılandırmasını sağlar.
    public MultipartJacksonConfig(ObjectMapper objectMapper, MappingJackson2HttpMessageConverter converter) {
        List<MediaType> supportedMediaTypes = new ArrayList<>(); // Desteklenen medya türlerini tutacak bir liste oluşturulur.
        supportedMediaTypes.add(MediaType.TEXT_PLAIN); // Desteklenen medya türleri listesine TEXT_PLAIN eklenir.
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM); // Desteklenen medya türleri listesine APPLICATION_OCTET_STREAM eklenir.
        converter.setSupportedMediaTypes(supportedMediaTypes); // Converter'ın desteklenen medya türleri ayarlanır.

    }
}
