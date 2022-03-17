package com.alkemy.ong.data.gateways;

import com.alkemy.ong.domain.mail.MailGateway;
import com.alkemy.ong.web.utils.MailUtils;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultMailGateway implements MailGateway  {

    @Autowired
    SendGrid sendgrid;

    @Value("${spring.sendgrid.template-id}")
    String templateId;

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
    public Boolean sendMailWithTemplate(String to, String name, String body) {

        Email from = new Email(MailUtils.MAIL_FROM, MailUtils.MAIL_FROM_NAME);
        Email toEmail = new Email(to);
        Mail mail = new Mail();

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);
        mail.setFrom(from);

        personalization.addDynamicTemplateData("firstName", name);
        personalization.addDynamicTemplateData("body", body);
        mail.addPersonalization(personalization);
        mail.setTemplateId(templateId);

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
