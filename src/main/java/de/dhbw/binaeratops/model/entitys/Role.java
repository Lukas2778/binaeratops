package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.RoleI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
 * @see RoleI
 *
 * @author Nicolas Haug
 */
@Entity
public class Role implements RoleI {

    @Id
    @GeneratedValue
    private Long roleId;

    private String roleName;

    private String description;

    public Role(String ARoleName, String ADescription) {

    }

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
