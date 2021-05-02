package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.model.enums.ItemType;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.NPCRepositoryI;
import de.dhbw.binaeratops.model.repository.RaceRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfiguratorService implements ConfiguratorServiceI {

    private Dungeon dungeon;
    private User dungeonDesigner;

    @Autowired
    DungeonRepositoryI dungeonRepo;
    @Autowired
    NPCRepositoryI npcRepo;
    @Autowired
    RaceRepositoryI raceRepo;
    @Autowired
    RoomRepositoryI roomRepo;

    public ConfiguratorService(){
        dungeon = new Dungeon();
        dungeon.setDungeonName(" ");
        dungeon.setDungeonMasterId(0L);
        dungeon.setStartRoomId(0L);
        dungeon.setDefaultInventoryCapacity(0L);
        dungeon.setCommandSymbol('f');
        dungeon.setDungeonStatus(Status.INACTIVE);
        dungeon.setDungeonVisibility(Visibility.IN_CONFIGURATION);
        dungeon.setPlayerMaxSize(3L);
    }

    @Override
    public Dungeon createDungeon(String AName, User AUser) {
        dungeonDesigner = AUser;
        dungeon = new Dungeon(AName,dungeonDesigner.getUserId());
        dungeonRepo.save(dungeon);
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
        Race testRace = raceRepo.findByRaceId(5L);
        NPC newNPC = new NPC(AName, testRace, ADescription);
        raceRepo.save(testRace);
        npcRepo.save(newNPC);
        dungeon.getNpcs().add(newNPC);
        dungeonRepo.save(dungeon);
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
    public void createRoom(String AName) {
        Room newRoom = new Room(AName);
        roomRepo.save(new Room());
        dungeon.getRooms().add(newRoom);
        dungeonRepo.save(dungeon);
    }

    @Override
    public void setNeighborRoom(Direction ADirection, Long ARoomId, Long ANeighborRoom) {

    }

    @Override
    public void removeNeighborRoom(String ADirection) {

    }

    @Override
    public void setItems(Room ARoom, List<Item> AItemList) {
        ARoom.getItems().clear();
        ARoom.getItems().addAll(AItemList);
        roomRepo.save(ARoom);
    }

    @Override
    public void setNPCs(Room ARoom, List<NPC> ANPCList) {
        ARoom.getNpcs().clear();
        ARoom.getNpcs().addAll(ANPCList);
        roomRepo.save(ARoom);
    }

    @Override
    public List<Item> getAllItems() {
        return dungeon.getItems();
    }

    @Override
    public List<NPC> getAllNPCs() {
        return dungeon.getNpcs();
    }

    @Override
    public void deleteRoom(Room ARoom) {
        dungeon.getRooms().remove(ARoom);
        dungeonRepo.save(dungeon);
        roomRepo.delete(ARoom);
    }

    @Override
    public Room getRoom(Long ARoomID) {
        return null;
    }
}
