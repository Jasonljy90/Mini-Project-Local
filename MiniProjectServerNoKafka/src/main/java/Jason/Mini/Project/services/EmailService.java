package Jason.Mini.Project.services;

// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    // @Value("${SENDGRID_API_KEY}")
    // private String apiKey;

    private String apiKey = "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";

    public boolean sendEmail(String userEmail, String subjects, String message) {
        Boolean sentSuccessfully = true;
        Email from = new Email("jasonljy90@gmail.com");
        String subject = subjects;
        Email to = new Email(userEmail);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        // SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            sentSuccessfully = false;
        }
        return sentSuccessfully;
    }
}
