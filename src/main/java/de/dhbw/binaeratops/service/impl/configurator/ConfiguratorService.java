package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.ItemType;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.List;

public class ConfiguratorService implements ConfiguratorServiceI {

    private Dungeon dungeon;
    private User dungeonDesigner;


    public ConfiguratorService(User AUser){
        dungeonDesigner = AUser;
    }

    public ConfiguratorService(User AUser, Dungeon ADungeon){
        dungeonDesigner = AUser;
        dungeon = ADungeon;
    }

    @Override
    public Dungeon createDungeon(String AName) {
        dungeon = new Dungeon(AName, dungeonDesigner.getUserId());
        return dungeon;
        }

    @Override
    public void setStartRoom(Room ARoom) {

    }

    @Override
    public void setCommandSymbol(String ACommandSymbol) {

    }

    @Override
    public void setMaxPlayercount(int ACount) {

    }

    @Override
    public List<Room> getAllDungeonRooms() {
        return null;
    }

    @Override
    public void createItem(String AName, ItemType AType, String ADescription, int ASize) {

    }

    @Override
    public void deleteItem(Item AItem) {

    }

    @Override
    public Item getItem(Long AItemId) {
        return null;
    }

    @Override
    public void createNPC(String AName, String ADescription, Race ARace) {

    }

    @Override
    public void deleteNPC(NPC ANPC) {

    }

    @Override
    public Item getNPC(Long ANPCId) {
        return null;
    }

    @Override
    public void createRole(String AName, String ADescription) {

    }

    @Override
    public void removeRole(Role ARole) {

    }

    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public void createRace(String AName, String ADescription) {

    }

    @Override
    public void removeRace(Race ARace) {

    }

    @Override
    public List<Race> getAllRace() {
        return null;
    }

    @Override
    public void setDefaultInventoryCapacity(int ACapacity) {

    }

    @Override
    public void createRoom(String AName, String ADescription) {

    }

    @Override
    public void setNeighborRoom(String ADirection, Long ARoomId) {

    }

    @Override
    public void removeNeighborRoom(String ADirection) {

    }

    @Override
    public void setItems(List<Item> AItemList) {

    }

    @Override
    public void setNPCs(List<NPC> ANPCList) {

    }

    @Override
    public List<Item> getAllRoomItems() {
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        return null;
    }

    @Override
    public List<NPC> getAllRoomNPCs() {
        return null;
    }

    @Override
    public List<NPC> getAllNPCs() {
        return null;
    }

    @Override
    public void deleteRoom(Long ARoomID) {

    }

    @Override
    public Room getRoom(Long ARoomID) {
        return null;
    }
}
