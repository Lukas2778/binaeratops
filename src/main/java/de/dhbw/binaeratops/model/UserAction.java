package de.dhbw.binaeratops.model;

public class UserAction {
    public UserAction(String userActionMessage) {
        UserActionMessage = userActionMessage;
    }

    public String getUserActionMessage() {
        return UserActionMessage;
    }

    private String UserActionMessage;


}
