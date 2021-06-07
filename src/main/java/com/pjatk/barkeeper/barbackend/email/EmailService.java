package com.pjatk.barkeeper.barbackend.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(String receiver, String emailMessage) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(emailMessage, true);
            helper.setTo(receiver);
            helper.setSubject("Confirm your Barkeeper App registration");
            helper.setFrom("confirmation@barkeeper.com");
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("Failed to send message", e);
            throw new IllegalStateException("Failed to send email");
        }

    }
}
