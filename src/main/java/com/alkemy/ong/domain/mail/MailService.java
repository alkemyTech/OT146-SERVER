package com.alkemy.ong.domain.mail;

public class MailService {

    private final MailGateway mailGateway; 

    public MailService(MailGateway mailGateway) {
        this.mailGateway = mailGateway;
    }

    public String sendMail(String to, String subject, String body) {
        return mailGateway.sendMail(to, subject, body);
    }

}
