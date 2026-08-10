package com.example.iksystem;

import com.example.iksystem.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProtocolScheduler {
    private final ProtocolRepository protocolRepository;
    private final EmailService emailService;
    @Value("${app.hr.notification-email}") private String hrEmail;

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at 1 AM
    public void checkProtocolExpirations() {
        LocalDate today = LocalDate.now();
        List<ProtocolsEntity>in30Days=protocolRepository.findAllByEndDateAndProtocolStatusTrue(today.plusDays(30));
        for (ProtocolsEntity protocol : in30Days) {
            emailService.sendProtocolExpirationNotice(hrEmail, protocol.getTitle(),30);
            log.info("Sending protocol expiration notice for left 30 days "+protocol.getTitle());
        }
        List<ProtocolsEntity>in7Days=protocolRepository.findAllByEndDateAndProtocolStatusTrue(today.plusDays(7));
        for(ProtocolsEntity protocol : in7Days) {
            emailService.sendProtocolExpirationNotice(hrEmail, protocol.getTitle(),7);
            log.info("Sending protocol expiration notice for left 7 days "+protocol.getTitle());
        }

    }
    @Scheduled(cron = "0 5 0 * * ?") // Runs every day at 2 AM
    public void autoDeactiveExpiredProtocols() {

        LocalDate today = LocalDate.now();
        List<ProtocolsEntity>expiredProtocols=protocolRepository.findAllByEndDateBeforeAndProtocolStatusTrue(today);
        if (!expiredProtocols.isEmpty()) {
            for (ProtocolsEntity protocol : expiredProtocols) {
                protocol.setProtocolStatus(false);
                log.info("Auto deactivation of expired protocol "+protocol.getTitle());
            }
            protocolRepository.saveAll(expiredProtocols);
            log.info("Auto deactivation of all protocols "+expiredProtocols.size());

        }


    }


}
