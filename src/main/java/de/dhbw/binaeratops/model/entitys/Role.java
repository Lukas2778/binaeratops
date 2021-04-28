package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.RoleI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
}
