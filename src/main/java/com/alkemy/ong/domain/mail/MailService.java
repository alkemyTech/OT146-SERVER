package com.alkemy.ong.domain.mail;

import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final MailGateway mailGateway; 

    public MailService(MailGateway mailGateway) {
        this.mailGateway = mailGateway;
    }

    public Boolean sendMail(String to, String subject, String body) {
        return mailGateway.sendMail(to, subject, body);
    }

    public Boolean sendMailWithTemplate(String to, String subject, String title, String body) {
        return mailGateway.sendMailWithTemplate(to, subject, title, body);
    }



    
}
