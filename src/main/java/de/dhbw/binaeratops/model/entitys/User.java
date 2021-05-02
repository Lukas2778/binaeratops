package de.dhbw.binaeratops.model.entitys;


import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entity Objekt für einen Benutzer.
 * <p>
 * Es repräsentiert die Entity "Benutzer" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Benutzer Schnittstelle.
 *
 * @see UserI
 *
 * @author Matthias Rall, Nicolas Haug
 */
@Entity
public class User implements UserI {
    @Id
    @GeneratedValue
    private Long userId;

    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String passwordHash;

    private Integer code;

    private Boolean isVerified;

    @ManyToMany
    private final List<Avatar> myAvatars = new ArrayList<>();

    @ManyToMany
    private final List<Dungeon> myDungeons = new ArrayList<>();

    /**
     * Konstruktor zum Erzeugen eines Benutzers mit allen Eigenschaften.
     *
     * @param AName       Name des Benutzers.
     * @param AEmail      E-Mail des Benutzers.
     * @param APassword   Passwort des Benutzers.
     * @param ACode       Verifizierungscode des Benutzers.
     * @param AIsVerified Verifizierungsstatus, ob Konto verifiziert ist.
     */
    public User(@NotEmpty String AName, @NotEmpty @Email String AEmail, @NotEmpty String APassword, @NotEmpty int ACode, @NotEmpty boolean AIsVerified) {
        this.username = AName;
        this.email = AEmail;
        this.passwordHash = DigestUtils.sha1Hex(APassword);
        this.code = ACode;
        this.isVerified = AIsVerified;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines leeren Benutzers.
     */
    public User() {

    }

    public boolean checkPassword(String APassword) {
        return DigestUtils.sha1Hex(APassword).equals(passwordHash);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long AId) {
        this.userId = AId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String AUsername) {
        this.username = AUsername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String AEmail) {
        this.email = AEmail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String APasswordHash) {
        this.passwordHash = APasswordHash;
    }

    public void setPassword(String APassword) {
        this.passwordHash = DigestUtils.sha1Hex(APassword);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer ACode) {
        this.code = ACode;
    }

    public Boolean isVerified() {
        return isVerified;
    }

    public void setVerified(Boolean AIsVerified) {
        this.isVerified = AIsVerified;
    }

    public List<Avatar> getAvatars() {
        return myAvatars;
    }

    public List<Dungeon> getMyDungeons() {
        return myDungeons;
    }

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof User) {
            User other = (User) AOther;
            equals = (userId == other.userId
                    && (username == other.username || (username != null &&
                    username.equalsIgnoreCase(other.username))));
        }

        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("User[id = ").append(userId).append(" | name = ").append(username).append("]");
        return s.toString();
    }

    /**
     * Prüft die Gültigkeit eines Objekts und konvertiert das API-Interface zur
     * erwarteten Klasse der Implementation.
     *
     * @param AUser Objekt.
     * @return Objekt.
     * @throws InvalidImplementationException Objekt ungültig.
     */
    public static User check(UserI AUser) throws InvalidImplementationException {
        if (!(AUser instanceof User)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            User.class, AUser.getClass()));
        }

        return (User) AUser;
    }
}
