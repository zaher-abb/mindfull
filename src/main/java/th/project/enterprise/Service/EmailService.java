package th.project.enterprise.Service;

import th.project.enterprise.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void registrationConfirmationEmail(User user) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("zaher.abb12@gmail.com");
        String text = String.format("Welcome %s to Mindful App " +
                "  you have successfully registered", user.getFirstName());
        mail.setText(text);
        mail.setSubject("successfully registered");
        javaMailSender.send(mail);
    }

    public void userEmailToAdmin(User user, String text, String s) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("zaher.abb12@gmail.com");
        mail.setFrom("zaher.abb12@gmail.com");
        String TextMessage = String.format("message Sent From User with this Email :%S ", user.getEmail());
        mail.setText(TextMessage + "\n" + text);
        mail.setSubject(s);
        javaMailSender.send(mail);

    }

    public void orderConfirmationEmail(User user, LocalDateTime DelevieryDate) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("zaher.abb12@gmail.com");
        String text = String.format("Thank you  %s for you Order " +
                "  your order will be in your Adress at %s ", user.getFirstName(), DelevieryDate);
        mail.setText(text);
        mail.setSubject("Order Confirmation ");
        javaMailSender.send(mail);
    }

    public void emailAlertToSubmitSteps(List<User> userList, LocalDate date) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();

        for (User user : userList) {
            mail.setTo(user.getEmail());
            System.out.println(user.getEmail());
            mail.setFrom("zaher.abb12@gmail.com");
            String text = String.format("Hallo %s Sie haben Seit 24 Stunden Ihre Schritte Anzahl nicht eingegeben, bitte Rufen sie unsere " +
                    "HomePage und geben sie Ihre Schritte ein "
                    , user.getFirstName());
            mail.setText(text);
            mail.setSubject("Schritte Anzahl Eingabe");
            javaMailSender.send(mail);
            }

    }

}
