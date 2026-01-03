package com.adenali.fms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

//    @Value("${app.activation-url}")
    private String activationUrl="https://dev.adenali.com/api/authservice/user/activate";

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String to, String token) {

        String link = activationUrl + "?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("suport@adenali.com"); // <-- Add this
        message.setTo(to);
        message.setSubject("Activate your account");
        message.setText("""
                Welcome!
                
                Please activate your account by clicking the link below:
                %s
                
                This link expires in 24 hours.
                """.formatted(link));

        mailSender.send(message);
    }
}
