package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.mail.MailGateway;

import java.io.IOException;

import com.alkemy.ong.web.utils.MailUtils;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DefaultMailGateway implements MailGateway {

    @Autowired
    SendGrid sendgrid;


    @Override
    public Boolean sendMail(String to, String subject, String body) {

        Email email = new Email(MailUtils.MAIL_FROM, MailUtils.MAIL_FROM_NAME);

        Mail mail = new Mail(email, subject, new Email(to), new Content("text/plain", body));
        mail.setReplyTo(email);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            this.sendgrid.api(request);

            return true;

        } catch (IOException ex) {
            return false;

        }
    }

    @Override
    public Boolean sendMailWithTemplate(String to, String subject, String title, String body) {

        Email emailFrom = new Email(MailUtils.MAIL_FROM, MailUtils.MAIL_FROM_NAME);

        Email emailTo = new Email(to);

        Mail mail = new Mail(emailFrom, subject, emailTo, new Content("text/html", MailUtils.TEMPLATE));

        mail.personalization.get(0).addSubstitution("%title%", title);
        mail.personalization.get(0).addSubstitution("%body%", body);

        try {

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            this.sendgrid.api(request);

            return true;

        } catch (IOException ex) {
            return false;
        }

    }

}
