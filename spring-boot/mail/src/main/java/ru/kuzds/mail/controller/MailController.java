package ru.kuzds.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuzds.mail.dto.MultipleReceiverRequest;
import ru.kuzds.mail.dto.SingleReceiverRequest;
import ru.kuzds.mail.service.MailService;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/text")
    public void sendTextMail(@RequestBody SingleReceiverRequest request) {
        mailService.sendTextMail(request);
    }

    @PostMapping("/audio")
    public void sendAudioSample(@RequestBody SingleReceiverRequest request) {
        mailService.sendAudioSample(request);
    }

    @PostMapping("/html")
    public void sendHtmlMail(@RequestBody MultipleReceiverRequest request) {
        mailService.sendHtmlMail(request);
    }
}
