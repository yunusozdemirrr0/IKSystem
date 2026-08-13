package com.example.iksystem.service.impl;

import com.example.iksystem.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
/**
 * Bu sınıf Email Service Impl nesnesini temsil eder.
 */

@Service
@RequiredArgsConstructor
@Slf4j // Loglamayı etkinleştirir

public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender; // JavaMailSender nesnesi, e-posta gönderme işlemlerini gerçekleştirir
    @Value("${spring.mail.username}") private String fromAddress; // E-posta gönderen adresi, application.properties dosyasından alınır
    @Override
    //Protokol süresi dolmak üzere olan bir protokol için uyarı e-postası gönderir.
    public void sendProtocolExpirationNotice(String to, String title, Long daysLeft) {
        SimpleMailMessage message = new SimpleMailMessage(); // Basit bir e-posta mesajı oluşturur
        message.setFrom(fromAddress); // E-posta gönderen adresini ayarlar
        message.setTo(to); // E-posta alıcı adresini ayarlar
        message.setSubject("UYARI:Protokol Süresi Dolmak Üzere - "+title); // E-posta konusunu ayarlar
        message.setText("Sayın İK Yöneticisi,\n\n'" + title + "' isimli protokolün süresinin dolmasına " + daysLeft +
                " gün kalmıştır.\nLütfen gerekli güncellemeleri yapınız.");

        try { // E-posta gönderme işlemi sırasında oluşabilecek hataları yakalamak için try-catch bloğu kullanılır
            mailSender.send(message);
            log.info("Protocol expiration notice sent to: {}", to); // E-posta gönderme işlemi başarılı olduğunda log kaydı oluşturur
        } catch (Exception e) { // E-posta gönderme işlemi sırasında bir hata oluşursa log kaydı oluşturur
            log.error("Failed to send protocol expiration notice to: {}", to, e);
        }
    }
}
