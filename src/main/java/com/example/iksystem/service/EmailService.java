package com.example.iksystem.service;
/**
 * Bu arayüz Email Service davranışlarını tanımlar.
 */

public interface EmailService {
    void sendProtocolExpirationNotice(String to, String title, Long daysLeft);
}
