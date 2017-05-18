package com.epam.lab.mentoring.mail;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Collections;
import java.util.List;

@Service
public class SendMailService {
    private static final Logger log = LoggerFactory.getLogger(SendMailService.class);

    @Autowired
    private EmailService emailService;

    @Async
    public void notifyUser(List<String> newBooks, String from, String to) {
        try {
            Email email = DefaultEmail.builder()
                    .from(new InternetAddress(from))
                    .to(Collections.singletonList(new InternetAddress(to)))
                    .subject("Books notification")
                    .body("Here is the list of new books: " + newBooks)
                    .encoding("UTF-8").build();

            emailService.send(email);
        } catch (AddressException e) {
            log.error("Failed to send email!", e);
        }
    }
}
