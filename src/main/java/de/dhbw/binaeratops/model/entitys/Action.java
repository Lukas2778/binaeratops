package de.dhbw.binaeratops.model.entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Action {

    @Id
    @GeneratedValue
    private Long actionId;

    private String name;
    private String action;

    Action (String name, String action) {
        this.name = name;
        this.action = action;
    }

    public Action() {
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
