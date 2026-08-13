package com.example.iksystem.scheduler;

import com.example.iksystem.entity.ProtocolsEntity;
import com.example.iksystem.entity.UsersEntity;
import com.example.iksystem.enums.model.constant.ProtocolStatus;
import com.example.iksystem.enums.model.constant.Role;
import com.example.iksystem.repository.ProtocolRepository;
import com.example.iksystem.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.iksystem.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
/**
 * Bu sınıf Protocol Scheduler nesnesini temsil eder.
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class ProtocolScheduler {
    private final ProtocolRepository protocolRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    // Bu metod, protokol bitiş tarihlerini kontrol eder ve belirli bir süre (30 gün ve 7 gün) kalan protokoller için insan kaynaklarına bildirim e-postası gönderir. Bu işlem her gün saat 0'da çalışır.
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void checkProtocolExpirations() {
        log.info("Checking protocol expirations");
        LocalDate today = LocalDate.now();// Bugünün tarihini alır

        List<UsersEntity> hrManagers = userRepository.findAllByRole(Role.IK_ADMIN);
        if (hrManagers.isEmpty()) {
            log.warn("No HR managers found for protocol expiration notifications.");
            return;
        }


        // 30 gün ve 7 gün kalan protokolleri bulur ve insan kaynaklarına bildirim e-postası gönderir.
        List<ProtocolsEntity>in30Days=protocolRepository.findAllByEndDateAndProtocolStatus(today.plusDays(30), ProtocolStatus.ACTIVE);
        for (ProtocolsEntity protocol : in30Days) {
            sendEmailToHRManagers(hrManagers, protocol.getTitle(), 30L);
            // Log kaydı oluşturur
            log.info("Sending protocol expiration notice for left 30 days "+protocol.getTitle());
        }

        // 7 gün kalan protokolleri bulur ve insan kaynaklarına bildirim e-postası gönderir.
        List<ProtocolsEntity>in7Days=protocolRepository.findAllByEndDateAndProtocolStatus(today.plusDays(7), ProtocolStatus.ACTIVE);
        for(ProtocolsEntity protocol : in7Days) {
            sendEmailToHRManagers(hrManagers, protocol.getTitle(), 7L);
            log.info("Sending protocol expiration notice for left 7 days "+protocol.getTitle());
        }

    }
    //Bu metod, protokol bitiş tarihlerini kontrol eder ve süresi dolmuş protokolleri otomatik olarak devre dışı bırakır. Bu işlem her gün saat 0:05'te çalışır.
    @Scheduled(cron = "0 5 0 * * ?")
    @Transactional
    public void autoDeactiveExpiredProtocols() {

        LocalDate today = LocalDate.now();
        List<ProtocolsEntity>expiredProtocols=protocolRepository.findAllByEndDateBeforeAndProtocolStatus(today, ProtocolStatus.ACTIVE);// Süresi dolmuş protokolleri bulur

        // Eğer süresi dolmuş protokoller varsa, her birini devre dışı bırakır ve log kaydı oluşturur
        if (!expiredProtocols.isEmpty()) {
            for (ProtocolsEntity protocol : expiredProtocols) {
                protocol.setProtocolStatus(ProtocolStatus.PASSIVE);
                log.info("Auto deactivation of expired protocol "+protocol.getTitle());
            }
            protocolRepository.saveAll(expiredProtocols); // Değişiklikleri veritabanına kaydeder
            log.info("Auto deactivation of all protocols "+expiredProtocols.size());

        }


    }

    private void sendEmailToHRManagers(List<UsersEntity> hrManagers, String title, Long daysLeft) {
        for (UsersEntity hrManager : hrManagers) {
            if(hrManager.getRole().equals(Role.IK_ADMIN)) {
                emailService.sendProtocolExpirationNotice(hrManager.getEmail(), title, daysLeft);
                log.info("Sent email to HR manager: " + hrManager.getEmail());
            }
        }
    }


}
