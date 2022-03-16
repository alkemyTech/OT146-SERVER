package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.mail.MailGateway;

import java.io.IOException;

import com.alkemy.ong.domain.mail.EmailRequest;
import com.alkemy.ong.domain.mail.MailGateway;
import com.alkemy.ong.web.utils.MailUtils;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMailGateway implements MailGateway  {

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
    
}
