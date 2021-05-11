package de.dhbw.binaeratops.service.impl.registration;

import de.dhbw.binaeratops.model.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Komponente "MailService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten für den Mail-Versand bereit.
 * </p>
 *
 * @author Matthias Rall
 */
@Service
public class MailService {

    private JavaMailSender javaMailSender;

    /**
     * Konstruktor zum Erzeugen des MailService.
     *
     * @param AJavaMailSender JavaMailSender.
     */
    @Autowired
    public MailService(JavaMailSender AJavaMailSender) {
        this.javaMailSender = AJavaMailSender;
    }

    /**
     * Sendet die verifizierungs E-Mail an die Adresse, die im Benutzer gespeichert ist.
     * <p>
     * Diese Email beinhaltet einen Verifizierungscode.
     *
     * @param AUser Benutzer mit E-Mail Adresse, an welche die E-Mail gesendet werden soll.
     * @param ACode Code welcher zur Verifizierung mit der E-Mail mitgeschickt wird.
     * @throws MailException Kann die E-Mail nicht gesendet werden, wird eine Exception geworfen.
     */
    public void sendVerificationMail(User AUser, int ACode) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(AUser.getEmail());
        mail.setFrom("binaeratops@gmail.com");
        mail.setSubject("Bitte bestätige deine E-Mail Adresse");
        mail.setText("Hallo " + AUser.getName() +
                ",\nbitte bestätige deine E-Mail Adresse, indem du den folgenden Code auf unserer Webseite eingibst:" +
                "\n\n" + Integer.toString(ACode) +
                "\n\n" +
                "Mit freundlichen Grüßen,\n" +
                "dein Binäratops-Team");

        javaMailSender.send(mail);
    }

    /**
     * Sendet die "Passwort ändern" E-Mail an die Adresse, die im Benutzer gespeichert ist.
     * <p>
     * Diese Email beinhaltet einen Verifizierungscode.
     *
     * @param AUser Benutzer mit E-Mail Adresse, an welche die E-Mail gesendet werden soll.
     * @param ACode Code welcher zur Verifizierung mit der E-Mail mitgeschickt wird.
     */
    public void sendPasswordMail(User AUser, int ACode) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(AUser.getEmail());
        mail.setFrom("binaeratops@gmail.com");
        mail.setSubject("Bitte bestätige deine E-Mail Adresse");
        mail.setText("Hallo " + AUser.getName() +
                ",\nbitte bestätige, dass du dein Passwort wirklich ändern willst," +
                " indem du den folgenden Code auf unserer Webseite eingibst:" +
                "\n\n" + Integer.toString(ACode) +
                "\n\n" +
                "Mit freundlichen Grüßen,\n" +
                "dein Binäratops-Team");

        javaMailSender.send(mail);
    }
}
