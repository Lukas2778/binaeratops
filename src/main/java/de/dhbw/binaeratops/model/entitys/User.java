package de.dhbw.binaeratops.model.entitys;


import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String eMail;

    @NotEmpty
    private String passwordHash;

    public User(@NotEmpty String name, @NotEmpty @Email String eMail, @NotEmpty String password) {
        this.name = name;
        this.eMail = eMail;
        this.passwordHash= DigestUtils.sha1Hex(password);
    }

    public boolean checkPassword(String password){
        return DigestUtils.sha1Hex(password).equals(passwordHash);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
