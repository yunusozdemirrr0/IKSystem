
package com.example.iksystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

 /**
 * Bu sınıf Security Config nesnesini temsil eder.
 */


@Configuration
@EnableWebSecurity

public class SecurityConfig {
    // Bu metod, güvenlik filtre zincirini yapılandırır.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // CSRF korumasını devre dışı bırakır
                .authorizeRequests(auth -> auth // İstekleri yetkilendirme kurallarını belirler
                        //.requestMatchers("/api/v1/admin/**") // "/api/v1/admin/**" ile başlayan isteklere sadece "IK_ADMIN" rolüne sahip kullanıcıların erişmesine izin verir
                        //.hasRole("IK_ADMIN")
                        .anyRequest().permitAll() // Diğer tüm isteklere izin verir
                );
        return http.build(); // Güvenlik filtre zincirini oluşturur ve döndürür
    }

}
