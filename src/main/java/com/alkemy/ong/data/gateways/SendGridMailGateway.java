package com.alkemy.ong.data.gateways;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import com.alkemy.ong.domain.mail.EmailRequest;
import com.alkemy.ong.web.utils.MailUtils;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.sendgrid.helpers.mail.objects.Content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendGridMailGateway {

    @Autowired
    SendGrid sendgrid;

    public Response sendemail(EmailRequest emailrequest) {

        Mail mail = new Mail(new Email(MailUtils.MAIL_FROM), emailrequest.getSubject(), new Email(emailrequest.getTo()), new Content("text/plain", emailrequest.getBody()));
        mail.setReplyTo(new Email("abc@gmail.com"));


        Request request = new Request();

        Response response = null;

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            response=this.sendgrid.api(request);

        } catch (IOException ex) {
            throw new RuntimeException();

        }

        return response;
    }
    
}
