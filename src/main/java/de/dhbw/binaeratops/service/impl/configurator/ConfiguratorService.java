package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.model.enums.ItemType;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.repository.*;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Scope(value = "session")
@Service
public class ConfiguratorService implements ConfiguratorServiceI {

    private Dungeon dungeon;
    private User dungeonDesigner;

    @Autowired
    DungeonRepositoryI dungeonRepo;
    @Autowired
    NPCRepositoryI npcRepo;
    @Autowired
    RoleRepositoryI roleRepo;
    @Autowired
    RaceRepositoryI raceRepo;
    @Autowired
    RoomRepositoryI roomRepo;
    @Autowired
    ItemRepositoryI itemRepo;

    public void init(DungeonRepositoryI ADungeonRepo) {
        dungeonRepo = ADungeonRepo;
    }


    @Override
    public Dungeon createDungeon(String AName, User AUser, Long APlayerSize, Visibility AVisibility) {
        dungeonDesigner = AUser;
        dungeon = new Dungeon(AName, dungeonDesigner.getUserId(), APlayerSize, AVisibility);
        dungeonRepo.save(dungeon);
        return dungeon;
    }

    @Override
    public Dungeon getDungeon() {
        return dungeon;
    }

    @Override
    public void setDungeon(Long ADungeonId) {
        dungeon = dungeonRepo.findById(ADungeonId).get();
    }

    @Override
    public void saveDungeon() {
        dungeonRepo.save(dungeon);
    }

    @Override
    public void deleteDungeon(Long ADungeonId) {
        setDungeon(ADungeonId);
        dungeonRepo.delete(dungeon);
    }

    @Override
    public Dungeon createDungeon(String AName, User AUser) {
        dungeonDesigner = AUser;
        dungeon = new Dungeon(AName, dungeonDesigner.getUserId());
        AUser.addDungeon(dungeon);
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
    public void createItem(String AName, ItemType AType, String ADescription, Long ASize) {
        Item item = new Item(AName, ASize, ADescription);
        item.setType(AType);
        itemRepo.save(item);
        dungeon.addItem(item);
        dungeonRepo.save(dungeon);
    }

    @Override
    public void updateItem(Item AItem) {
        itemRepo.save(AItem);
    }

    @Override
    public void deleteItem(Item AItem) {
        dungeon.removeItem(AItem);
        dungeonRepo.save(dungeon);
        itemRepo.delete(AItem);
    }

    @Override
    public Item getItem(Long AItemId) {
        return null;
    }

    @Override
    public void createNPC(String AName, String ADescription, Race ARace) {
        NPC newNPC = new NPC(AName, ARace, ADescription);
        npcRepo.save(newNPC);
        dungeon.addNpc(newNPC);
        dungeonRepo.save(dungeon);
    }

    @Override
    public void updateNPC(NPC ANPC) {
        npcRepo.save(ANPC);
    }

    @Override
    public void deleteNPC(NPC ANPC) {
        dungeon.removeNpc(ANPC);
        dungeonRepo.save(dungeon);
        npcRepo.delete(ANPC);
    }

    @Override
    public Item getNPC(Long ANPCId) {
        return null;
    }

    @Override
    public void createRole(String AName, String ADescription) {
        Role newRole = new Role(AName, ADescription);
        roleRepo.save(newRole);
        dungeon.addRole(newRole);
        dungeonRepo.save(dungeon);
    }

    @Override
    public void removeRole(Role ARole) {
        dungeon.removeRole(ARole);
        roleRepo.delete(ARole);
        dungeonRepo.save(dungeon);
    }

    @Override
    public List<Role> getAllRoles() {
        return dungeon.getRoles();
    }

    @Override
    public void createRace(String AName, String ADescription) {
        Race newRace = new Race(AName, ADescription);
        raceRepo.save(newRace);
        dungeon.addRace(newRace);
        dungeonRepo.save(dungeon);
    }

    @Override
    public void removeRace(Race ARace) {
        dungeon.removeRace(ARace);
        raceRepo.delete(ARace);
        dungeonRepo.save(dungeon);

    }

    @Override
    public List<Race> getAllRace() {

        return dungeon.getRaces();
    }

    @Override
    public void setDefaultInventoryCapacity(int ACapacity) {

    }

    @Override
    public void createRoom(String AName) {
        Room newRoom = new Room(AName);
        roomRepo.save(new Room());
        dungeon.getRooms()
                .add(newRoom);
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
        for (Item myItem: AItemList) {
            myItem.setRoom(ARoom);
            itemRepo.save(myItem);
        }
        ARoom.getItems()
                .clear();
        ARoom.getItems()
                .addAll(AItemList);
    }

    @Override
    public void setNPCs(Room ARoom, List<NPC> ANPCList) {
        for (NPC myNpc : ANPCList) {
            myNpc.setRoom(ARoom);
            npcRepo.save(myNpc);
        }
        ARoom.getNpcs()
                .clear();
        ARoom.getNpcs()
                .addAll(ANPCList);
    }

    @Override
    public List<Item> getAllItems() {

        return dungeon.getItems();
    }
    @Override
    public List<Item> getAllItems(Room ARoom){
        List<Item> items = new ArrayList<>();
        for (Item myItem: dungeon.getItems()) {
            if(myItem.getRoom() != null){
                if (myItem.getRoom().getRoomId() == ARoom.getRoomId()){
                    items.add(myItem);
                }
            }
        }
        return items;
    }

    @Override
    public List<NPC> getAllNPCs() {
        return dungeon.getNpcs();
    }

    @Override
    public List<NPC> getAllNPCs(Room ARoom){
        List<NPC> npcs = new ArrayList<>();
        for (NPC myNpc: dungeon.getNpcs()) {
            if(myNpc.getRoom() != null){
                if (myNpc.getRoom().getRoomId() == ARoom.getRoomId()){
                    npcs.add(myNpc);
                }
            }
        }
        return npcs;
    }

    @Override
    public void deleteRoom(Room ARoom) {
        dungeon.getRooms().remove(ARoom);
        //dungeonRepo.save(dungeon);
        //dungeonRepo.delete(dungeon);
        roomRepo.delete(ARoom);
    }

    @Override
    public Room getRoom(Long ARoomID) {
        return null;
    }

    @Override
    public void addRoom(Room ARoom) {
        //wenn der Raum existiert, wird er überschrieben, wenn nicht, wird ein neuer Raum in die Datenbank gespeichert
        roomRepo.save(ARoom);
        //Raum dem aktuellen Dungeon hinzufügen
        dungeon.addRoom(ARoom);
        //geupdateten Dungeon in die Datenbank speichern
        try {
            dungeonRepo.save(dungeon);
        } catch (Exception e) {
            roomRepo.save(ARoom);
            //Notification.show("Hier findet er den Entity nicht!");
        }
    }

    @Override
    public void saveRoom(Room ARoom) {
        roomRepo.save(ARoom);
        //TODO test: dungeonRepo.save(dungeon);
    }
}
