package com.example.demo123.service;

import com.example.demo123.dto.request.EmailRequest;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class Emailservice {

    @Autowired
    SendGrid sendGrid;

    public Response sendemail(EmailRequest emailrequest)
    {

        Mail mail = new Mail(new Email("sangfpt1999alhp@gmail.com"), emailrequest.getSubject(), new Email(emailrequest.getTo()),new Content("text/html", emailrequest.getBody()));
        mail.setReplyTo(new Email("abc@gmail.com"));
        Request request = new Request();

        Response response = null;

        try {

            request.setMethod(Method.POST);

            request.setEndpoint("mail/send");

            request.setBody(mail.build());

            response=this.sendGrid.api(request);

        } catch (IOException ex) {

            System.out.println(ex.getMessage());

        }

        return response;


    }
}
