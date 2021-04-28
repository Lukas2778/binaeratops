package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;

import java.util.List;

public interface DungeonI {

    Long getDungeonId();

    void setDungeonId(Long ADungeonId);

    String getDungeonName();

    void setDungeonName(String ADungeonName);

    Visibility getDungeonVisibility();

    void setDungeonVisibility(Visibility ADungeonVisibility);

    Status getDungeonStatus();

    public void setDungeonStatus(Status ADungeonStatus);

    Long getDungeonMasterId();

    void setDungeonMasterId(Long ADungeonMasterId);

    Long getPlayerCount();

    void setPlayerCount(Long APlayerCount);

    Long getPlayerMaxSize();

    void setPlayerMaxSize(Long APlayerMaxSize);

    Long getStartRoomId();

    void setStartRoomId(Long AStartRoomId);

    Long getDefaultInventoryCapacity();

    void setDefaultInventoryCapacity(Long ADefaultInventoryCapacity);

    Character getCommandSymbol();

    void setCommandSymbol(Character ACommandSymbol);

    List<Avatar> getAvatars();

    List<User> getAllowedUsers();

    List<User> getBlockedUsers();

    List<Room> getRooms();

    List<Role> getRoles();

    List<Race> getRaces();
}
