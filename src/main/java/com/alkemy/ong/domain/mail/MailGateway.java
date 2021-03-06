package com.alkemy.ong.domain.mail;

public interface MailGateway {

    Boolean sendMail(String to, String subject, String body);

    Boolean sendMailWithTemplate(String to, String subject, String title, String body);
}
