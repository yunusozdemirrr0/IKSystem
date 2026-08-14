package com.example.iksystem.config;

import org.springframework.context.annotation.Configuration;
/**
 * Bu sınıf Cors Config nesnesini temsil eder.
 */

@Configuration

public class CorsConfig implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    // Bu metod, CORS (Cross-Origin Resource Sharing) yapılandırmasını sağlar.
    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/**") // Tüm yollar için CORS yapılandırmasını uygular.
                .allowedOriginPatterns("http://10.1.4.26:4200","http://localhost:4200") // İzin verilen kaynakları belirtir.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH") // İzin verilen HTTP yöntemlerini belirtir.
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    @Override
    public void addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**") // "/uploads/**" yoluna gelen istekleri yakalar.)
                .addResourceLocations("file:uploads/"); // "uploads" klasörünü kaynak olarak belirtir.
    }

}
