package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.PermissionI;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Entity Objekt für eine Permission
 * <p>
 * Es repräsentiert die Entity "Permission" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Permission Schnittstelle.
 * <p>
 *
 * @author Pedro Treuer
 * @see AvatarI
 */
@Entity
public class Permission implements PermissionI
{

    @Id
    @GeneratedValue
    private Long permissionId;


    private Boolean access;

    @ManyToOne
    private User user;

    @ManyToOne
    private Dungeon dungeon;



    /**
     * Konstruktor zum Erzeugen eines Avatars mit allen Eigenschaften.
     *
     * @param ADungeon Der Dungeon, für die der Spieler berechtigt wurde
     * @param AUser    Der Spieler, der berechtigt wurde
     * @param AAccess    Die Berechtigung
     */
    public Permission(Dungeon ADungeon, User AUser, Boolean AAccess) {
        this.dungeon = ADungeon;
        this.user = AUser;
        this.access = AAccess;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines Avatars.
     */
    public Permission() {

    }

    @Override
    public Dungeon getDungeon()
    {
        return dungeon;
    }

    @Override
    public void setDungeon(Dungeon ADungeon)
    {
        this.dungeon = ADungeon;
    }

    @Override
    public User getUser()
    {
        return user;
    }

    @Override
    public void setUser(User AUser)
    {
        this.user = AUser;
    }

    @Override
    public Boolean getAccess()
    {
        return access;
    }

    @Override
    public void setAccess(Boolean AAccess)
    {
        this.access = AAccess;
    }




    }


