package de.dhbw.binaeratops.service.impl.registration;


import de.dhbw.binaeratops.model.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class MailService {


    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendVerificationMail(User user, int code)throws MailException {
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("binaeratops@gmail.com");
        mail.setSubject("Bitte bestätige deine E.Mail Adresse");
        mail.setText("Hallo "+user.getName()+
                ",\nbitte bestätige deine E-Mail Adreese, indem du den folgenden Code auf unserer Webseite eingibst:" +
                "\n\n" +Integer.toString(code) +
                        "\n\n" +
                "Mit freundlichen Grüßen,\n" +
                "dein Binäratops-Team");

        javaMailSender.send(mail);
    }

}
