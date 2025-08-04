package com.curso.v0.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationManagerTest {

    @Mock
    private IEmailService emailService;
    
    @InjectMocks
    private NotificationManager notificationManager;
    
//    @BeforeEach
//    void setUp() {
//        notificationManager = new NotificationManager(emailService);
//    }
    
    @Test
    void testRegisterUser_ValidEmail_Success() {
        // Arrange
        String email = "juan@test.com";
        String name = "Juan";
        when(emailService.isEmailValid(email)).thenReturn(true);
        when(emailService.sendWelcomeEmail(email, name)).thenReturn(true);
        
        // Act
        String result = notificationManager.registerUser(email, name);
        
        // Assert
        assertEquals("Usuario registrado exitosamente. Email de bienvenida enviado.", result);
        
        // Verify - Verificamos las interacciones con el mock
        verify(emailService).isEmailValid(email);
        verify(emailService).sendWelcomeEmail(email, name);
    }
    
    @Test
    void testRegisterUser_InvalidEmail() {
        // Arrange
        String email = "email-invalido";
        String name = "Juan";
        when(emailService.isEmailValid(email)).thenReturn(false);
        
        // Act
        String result = notificationManager.registerUser(email, name);
        
        // Assert
        assertEquals("Email inválido", result);
        
        // Verify - Solo debe validar email, NO debe intentar enviar
        verify(emailService).isEmailValid(email);
        verify(emailService, never()).sendWelcomeEmail(anyString(), anyString());
    }
    
    @Test
    void testRegisterUser_EmailSendFails() {
        // Arrange
        String email = "juan@test.com";
        String name = "Juan";
        when(emailService.isEmailValid(email)).thenReturn(true);
        when(emailService.sendWelcomeEmail(email, name)).thenReturn(false);
        
        // Act
        String result = notificationManager.registerUser(email, name);
        
        // Assert
        assertEquals("Usuario registrado pero falló el envío del email de bienvenida.", result);
        
        // Verify
        verify(emailService).isEmailValid(email);
        verify(emailService).sendWelcomeEmail(email, name);
    }
    
    @Test
    void testRequestPasswordReset_Success() {
        // Arrange
        String email = "maria@test.com";
        when(emailService.isEmailValid(email)).thenReturn(true);
        when(emailService.sendPasswordResetEmail(eq(email), anyString())).thenReturn(true);
        
        // Act
        String result = notificationManager.requestPasswordReset(email);
        
        // Assert
        assertEquals("Email de recuperación enviado exitosamente", result);
        
        // Verify - Usamos anyString() porque el token se genera internamente
        verify(emailService).isEmailValid(email);
        verify(emailService).sendPasswordResetEmail(eq(email), anyString());
    }
    
    @Test
    void testSendCustomNotification_DailyLimitReached() {
        // Arrange
        String email = "test@test.com";
        when(emailService.getEmailsSentToday()).thenReturn(100);
        
        // Act
        String result = notificationManager.sendCustomNotification(email, "Asunto", "Mensaje");
        
        // Assert
        assertEquals("Límite diario de emails alcanzado", result);
        
        // Verify - Solo debe verificar límite, NO debe validar email ni enviar
        verify(emailService).getEmailsSentToday();
        verify(emailService, never()).isEmailValid(anyString());
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }
    
    @Test
    void testSendCustomNotification_Success() {
        // Arrange
        String email = "test@test.com";
        String subject = "Promoción especial";
        String message = "¡Oferta del 50% de descuento!";
        
        when(emailService.getEmailsSentToday()).thenReturn(50);
        when(emailService.isEmailValid(email)).thenReturn(true);
        when(emailService.sendEmail(email, subject, message)).thenReturn(true);
        
        // Act
        String result = notificationManager.sendCustomNotification(email, subject, message);
        
        // Assert
        assertEquals("Notificación enviada", result);
        //assertEquals("Límite diario de emails alcanzado", result);
        
        // Verify - Todas las interacciones en orden
        verify(emailService).getEmailsSentToday();
        verify(emailService).isEmailValid(email);
        verify(emailService).sendEmail(email, subject, message);
    }
    
    @Test
    void testSendCustomNotification_InvalidEmail() {
        // Arrange
        String email = "invalid-email";
        when(emailService.getEmailsSentToday()).thenReturn(10);
        when(emailService.isEmailValid(email)).thenReturn(false);
        
        // Act
        String result = notificationManager.sendCustomNotification(email, "Asunto", "Mensaje");
        
        // Assert
        assertEquals("Email inválido", result);
        
        // Verify
        verify(emailService).getEmailsSentToday();
        verify(emailService).isEmailValid(email);
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }
}