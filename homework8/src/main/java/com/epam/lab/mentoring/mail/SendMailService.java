package com.epam.lab.mentoring.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendMailService {

    public void notifyUser(List<String> newBooks, String to, String from) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setFrom(from);
        msg.setText("Here is the list of new books: " + newBooks);

        MailSender sender = new JavaMailSenderImpl();
        sender.send(msg);
    }
}
