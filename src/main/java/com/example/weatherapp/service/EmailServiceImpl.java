package com.example.weatherapp.service;

import com.example.weatherapp.exception.EmailSenderException;
import com.example.weatherapp.service.interfaces.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final Logger logger = Logger.getLogger(EmailServiceImpl.class.getName());

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@example.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        logger.info("Sending email to: " + to);

        try {
            mailSender.send(message);
        }
        catch (Exception e) {
          throw new EmailSenderException("Failed to send email", e);
        }

    }
}
