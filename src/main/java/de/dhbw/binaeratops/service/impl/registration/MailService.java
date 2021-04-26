package de.dhbw.binaeratops.service.impl.registration;

import de.dhbw.binaeratops.model.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService{

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    /**
     * Sendet die verifizierungs E-Mail an die Adresse, die in Benutzer gespeichert ist.
     * Diese Email beinhaltet einen Verifizierungscode.
     * @param AUser Benutzer mit E-Mail Adresse, an welche die E-Mail gesendet werden soll.
     * @param ACode Code welcher zur Verifizierung mit der E-Mail mitgeschickt wird.
     * @throws MailException Kann die E-Mail nicht gesendet werden, wird eine Exception geworfen.
     */
    public void sendVerificationMail(User AUser, int ACode) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(AUser.getEmail());
        mail.setFrom("binaeratops@gmail.com");
        mail.setSubject("Bitte bestätige deine E-Mail Adresse");
        mail.setText("Hallo "+AUser.getName()+
                ",\nbitte bestätige deine E-Mail Adresse, indem du den folgenden Code auf unserer Webseite eingibst:" +
                "\n\n" +Integer.toString(ACode) +
                        "\n\n" +
                "Mit freundlichen Grüßen,\n" +
                "dein Binäratops-Team");

        javaMailSender.send(mail);
    }
}
