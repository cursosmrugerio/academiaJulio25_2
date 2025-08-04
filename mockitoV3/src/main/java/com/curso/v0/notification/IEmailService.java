package com.curso.v0.notification;

public interface IEmailService {
    
    boolean sendEmail(String to, String subject, String body);
    
    boolean sendWelcomeEmail(String userEmail, String userName);
    
    boolean sendPasswordResetEmail(String userEmail, String resetToken);
    
    int getEmailsSentToday();
    
    boolean isEmailValid(String email);
}