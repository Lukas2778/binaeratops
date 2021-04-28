package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.NPC;

import java.util.List;

public interface RoomI {
    Long getRoomId();

    void setRoomId(Long ARoomId);

    String getRoomName();

    void setRoomName(String ARoomName);

    String getDescription();

    void setDescription(String ADescription);

    Long getNorthRoomId();

    void setNorthRoomId(Long ANorthRoomId);

    Long getEastRoomId();

    void setEastRoomId(Long AEastRoomId);

    Long getSouthRoomId();

    void setSouthRoomId(Long ASouthRoomId);

    Long getWestRoomId();

    void setWestRoomId(Long AWestRoomId);

    List<Item> getItems();

    List<NPC> getNpcs();
}
