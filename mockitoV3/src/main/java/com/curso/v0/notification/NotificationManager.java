package com.curso.v0.notification;

public class NotificationManager {
    
    private final IEmailService emailService;
    
    public NotificationManager(IEmailService emailService) {
        this.emailService = emailService;
    }
    
    public String registerUser(String email, String name) {
        if (!emailService.isEmailValid(email)) {
            return "Email inválido";
        }
        
        boolean emailSent = emailService.sendWelcomeEmail(email, name);
        
        if (emailSent) {
            return "Usuario registrado exitosamente. Email de bienvenida enviado.";
        } else {
            return "Usuario registrado pero falló el envío del email de bienvenida.";
        }
    }
    
    public String requestPasswordReset(String email) {
        if (!emailService.isEmailValid(email)) {
            return "Email inválido";
        }
        
        String resetToken = generateResetToken();
        boolean emailSent = emailService.sendPasswordResetEmail(email, resetToken);
        
        if (emailSent) {
            return "Email de recuperación enviado exitosamente";
        } else {
            return "Error al enviar email de recuperación";
        }
    }
    
    public String sendCustomNotification(String email, String subject, String message) {
        if (emailService.getEmailsSentToday() >= 100) {
            return "Límite diario de emails alcanzado";
        }
        
        if (!emailService.isEmailValid(email)) {
            return "Email inválido";
        }
        
        boolean sent = emailService.sendEmail(email, subject, message);
        return sent ? "Notificación enviada" : "Error al enviar notificación";
    }
    
    private String generateResetToken() {
        return "reset-token-" + System.currentTimeMillis();
    }
}