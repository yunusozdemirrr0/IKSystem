package com.example.iksystem.service.impl;

import com.example.iksystem.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String fromAddress;
    @Override
    public void sendProtocolExpirationNotice(String to, String title, long daysLeft) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject("UYARI:Protokol Süresi Dolmak Üzere - "+title);
        message.setText("Sayın İK Yöneticisi,\n\n'" + title + "' isimli protokolün süresinin dolmasına " + daysLeft +
                " gün kalmıştır.\nLütfen gerekli güncellemeleri yapınız.");
        try {
            mailSender.send(message);
            log.info("Protocol expiration notice sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send protocol expiration notice to: {}", to, e);
        }
    }
}
