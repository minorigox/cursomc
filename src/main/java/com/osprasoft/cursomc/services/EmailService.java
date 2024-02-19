package com.osprasoft.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.osprasoft.cursomc.domain.Pedido;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);
    void sendEmail(SimpleMailMessage msg);
    
}
