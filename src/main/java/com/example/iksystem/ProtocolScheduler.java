package com.example.iksystem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProtocolScheduler {
    @Scheduled(cron = "0 0 1 * * ?") // Runs every day at 1 AM
    public void checkAndManageProtocols() {
        log.info("Scheduled task started: Checking and managing protocols.");
        log.info("Scheduled task finished: Checking and managing protocols.");

    }
}
