package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.RoleI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entity Objekt für eine Rolle.
 * <p>
 * Es repräsentiert die Entity "Rolle" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Rolle Schnittstelle.
 * <p>
 *
 * @author Nicolas Haug
 * @see RoleI
 */
@Entity
public class Role implements RoleI {

    @Id
    @GeneratedValue
    private Long roleId;

    private String roleName;

    private String description;

    private Long lifepointsBonus;

    @ManyToOne
    private Dungeon dungeon;

    /**
     * Konstruktor zum Erzeugen einer Rolle mit allen Eigenschaften.
     *
     * @param ARoleName    Name der Rolle.
     * @param ADescription Beschreibung der Rolle.
     */
    public Role(String ARoleName, String ADescription, Long ALifepointsBonus) {
        this.roleName = ARoleName;
        this.description = ADescription;
        this.lifepointsBonus = ALifepointsBonus;
    }

    /**
     * Standardkonstruktor zum Erzeugen einer Rolle.
     */
    public Role() {

    }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long ARoleId) {
        this.roleId = ARoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String ARoleName) {
        this.roleName = ARoleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String ADescription) {
        this.description = ADescription;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Long getLifepointsBonus(){return lifepointsBonus; }

    public void setLifepointsBonus(Long ALifepointBonus){this.lifepointsBonus = ALifepointBonus; }

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof Role) {
            Role other = (Role) AOther;
            equals = (roleId == other.roleId);
            // && (name == other.name || (name != null &&
            //                    name.equalsIgnoreCase(other.name)))
        }

        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Rolle[ID = ")
                .append(roleId)
                .append(" | Name = ")
                .append(roleName)
                .append(" | Beschreibung = ")
                .append(description)
                .append("]\n");
        return s.toString();
    }

    /**
     * Prüft die Gültigkeit eines Objekts und konvertiert das API-Interface zur
     * erwarteten Klasse der Implementation.
     *
     * @param ARole Objekt.
     * @return Objekt.
     * @throws InvalidImplementationException Objekt ungültig.
     */
    public static Role check(RoleI ARole) throws InvalidImplementationException {
        if (!(ARole instanceof Role)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            Role.class, ARole.getClass()));
        }

        return (Role) ARole;
    }
}
