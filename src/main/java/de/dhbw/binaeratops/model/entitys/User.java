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
 * @author Matthias Rall, Nicolas Haug
 * @see UserI
 */
@Entity
public class User implements UserI {
    @Id
    @GeneratedValue
    private Long userId;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String passwordHash;

    private Integer code;

    private Boolean isVerified;

    @ManyToOne
    private Dungeon allowedDungeons;

    @ManyToOne
    private Dungeon blockedDungeons;

    @ManyToOne
    private Dungeon currentDungeon;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Avatar> myAvatars = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

    @Override
    public boolean checkPassword(String APassword) {
        return DigestUtils.sha1Hex(APassword).equals(passwordHash);
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long AId) {
        this.userId = AId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String AUsername) {
        this.name = AUsername;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String AEmail) {
        this.email = AEmail;
    }

    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public void setPassword(String APassword) {
        this.passwordHash = DigestUtils.sha1Hex(APassword);
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer ACode) {
        this.code = ACode;
    }

    @Override
    public Boolean isVerified() {
        return isVerified;
    }

    @Override
    public void setVerified(Boolean AIsVerified) {
        this.isVerified = AIsVerified;
    }

    @Override
    public Dungeon getAllowedDungeon() {
        return allowedDungeons;
    }

    @Override
    public void setAllowedDungeon(Dungeon AAllowedDungeons) {
        this.allowedDungeons = AAllowedDungeons;
    }

    @Override
    public Dungeon getBlockedDungeon() {
        return blockedDungeons;
    }

    @Override
    public void setBlockedDungeon(Dungeon ABlockedDungeons) {
        this.blockedDungeons = ABlockedDungeons;
    }

    @Override
    public Dungeon getCurrentDungeon() {
        return currentDungeon;
    }

    @Override
    public void setCurrentDungeon(Dungeon ACurrentDungeon) {
        this.currentDungeon = ACurrentDungeon;
    }

    @Override
    public void removeCurrentDungeon(){
        this.currentDungeon=null;
    }

    @Override
    public List<Avatar> getAvatars() {
        return myAvatars;
    }

    @Override
    public void addAvatar(Avatar AAvatar) {
        AAvatar.setUser(this);
        myAvatars.add(AAvatar);
    }

    @Override
    public void removeAvatar(Avatar AAvatar) {
        myAvatars.remove(AAvatar);
        AAvatar.setUser(null);
    }

    @Override
    public List<Dungeon> getMyDungeons() {
        return myDungeons;
    }

    @Override
    public void addDungeon(Dungeon ADungeon) {
        ADungeon.setUser(this);
        myDungeons.add(ADungeon);
    }

    @Override
    public void removeDungeon(Dungeon ADungeon) {
        myDungeons.remove(ADungeon);
        ADungeon.setUser(null);
    }


   

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof User) {
            User other = (User) AOther;
            equals = (userId.equals(other.userId)
                    && (name.equals(other.name) || (name != null &&
                    name.equalsIgnoreCase(other.name))));
        }

        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("User[id = ").append(userId).append(" | name = ").append(name).append("]");
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
