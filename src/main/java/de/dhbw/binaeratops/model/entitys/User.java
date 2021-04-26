package de.dhbw.binaeratops.model.entitys;


import de.dhbw.binaeratops.model.api.UserI;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * Entity Objekt für einen Benutzer.
 *
 * Es repräsentiert die Entity "Benutzer" der Datenbank in der Programmlogik.
 *
 * Es implementiert dazu alle Funktionalitäten der Benutzer Schnittstelle.
 *
 * @see UserI
 */
@Entity
public class User implements UserI {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String passwordHash;

    private Integer code;

    private Boolean isVerified;

    /**
     * Konstruktor zum Erzeugen eines Benutzers mit allen Eigenschaften.
     * @param AName Name des Benutzers.
     * @param AEmail E-Mail des Benutzers.
     * @param APassword Passwort des Benutzers.
     * @param ACode Verifizierungscode des Benutzers.
     * @param AIsVerified Verifizierungsstatus, ob Konto verifiziert ist.
     */
    public User(@NotEmpty String AName, @NotEmpty @Email String AEmail, @NotEmpty String APassword, @NotEmpty int ACode, @NotEmpty boolean AIsVerified) {
        this.name = AName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long AId) {
        this.id = AId;
    }

    public String getName() {
        return name;
    }

    public void setName(String AName) {
        this.name = AName;
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

    // TODO Equals Methoden anpassen + Javadoc

    @Override
    public boolean equals(Object AObject) {
        if (this == AObject) return true;
        if (AObject == null || getClass() != AObject.getClass()) return false;
        User user = (User) AObject;
        return id == user.id && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
