package com.alkemy.ong.domain.mail;

public interface MailGateway {

    String sendMail(String to, String subject, String body);
    
}
