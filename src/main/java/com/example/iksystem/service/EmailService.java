package com.example.iksystem.service;

public interface EmailService {
    void sendProtocolExpirationNotice(String to, String title, long daysLeft);
}
