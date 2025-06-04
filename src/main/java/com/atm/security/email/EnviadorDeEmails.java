package com.atm.security.email;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Random;


@Service
public class EnviadorDeEmails {


    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage templateMessage;

    public EnviadorDeEmails(MailSender mailSender, JavaMailSender javaMailSender, SimpleMailMessage templateMessage) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
        this.templateMessage = templateMessage;
    }


    public void enviarEmail(String destinatario, String assunto, String message) {

        // Create a thread-safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(destinatario);
        msg.setSubject(assunto);
        msg.setText(message);
        try {
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }

    }

    public void enviarEmailMIME(String destinatario, String assunto, String message, Principal principal) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO,
                                            new InternetAddress(destinatario));
                mimeMessage.setFrom(new InternetAddress("info@serverjava.net"));

                //mimeMessage.setText("Dear " + principal.getName() + " " +
                //        ", thanks for your order. " +
                //        "Your order number is " + String.valueOf(Math.random()));

                String content = new String(Files.readAllBytes(Paths.get("email.html")));
                System.out.println(content);
                mimeMessage.setContent(content, "text/html");
            }
        };

        try {
            this.javaMailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }

    }

}
