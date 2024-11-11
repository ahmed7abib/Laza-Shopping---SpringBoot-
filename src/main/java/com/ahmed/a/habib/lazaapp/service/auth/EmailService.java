package com.ahmed.a.habib.lazaapp.service.auth;

public interface EmailService {
    boolean sendEmail(String email, String subject, String body);
}