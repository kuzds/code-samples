package ru.kuzds.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.kuzds.mail.dto.MultipleReceiverRequest;
import ru.kuzds.mail.dto.SingleReceiverRequest;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.sender.email}")
    private String senderEmail;

    @Value("${spring.mail.sender.text}")
    private String senderText;

    public void sendTextMail(SingleReceiverRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(request.getReceiver());
        message.setSubject("Тестовое письмо");
        message.setText("Текстовое сообщение");
        mailSender.send(message);
    }

    public void sendAudioSample(SingleReceiverRequest request) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@kuzds.com");
            helper.setTo(request.getReceiver());
            helper.setSubject("Тестовое письмо с вложенным аудиофайлом");
            helper.setText("Текстовое сообщение");

            ClassPathResource classPathResource = new ClassPathResource("/static/audio_sample.mp3");
            File file = classPathResource.getFile();

            helper.addAttachment("audio_sample.mp3", file);
            mailSender.send(message);

        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHtmlMail(MultipleReceiverRequest request) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(new InternetAddress(senderEmail, senderText));
            helper.setTo(request.getReceivers().toArray(new String[0]));
            helper.setCc(request.getCopy());
            helper.setBcc(request.getHiddenCopy());
            helper.setSubject("Тестовое письмо");
            helper.setText("<p>Сообщение в формате <b>Html</b>.<br>Вторая строка.</p>", true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(message);
    }
}
