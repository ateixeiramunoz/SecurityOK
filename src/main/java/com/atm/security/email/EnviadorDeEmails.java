package com.atm.security.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class EnviadorDeEmails {


    private final MailSender mailSender;
    private final SimpleMailMessage templateMessage;

    public EnviadorDeEmails(MailSender mailSender, SimpleMailMessage templateMessage) {
        this.mailSender = mailSender;
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






}
