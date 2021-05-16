package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.model.enums.ItemType;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.repository.*;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Komponente "ConfiguratorService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Konfigurieren eines Dungeons bereit.
 * </p>
 * <p>
 * Für Schnittstelle dieser Komponente siehe @{@link ConfiguratorServiceI}.
 * </p>
 *
 * @author Timon Gartung, Pedro Treuer, Nicolas Haug, Lukas Göpel, Matthias Rall, Lars Rösel
 */
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
    @Autowired
    UserRepositoryI userRepo;
    @Autowired
    ItemInstanceRepositoryI itemInstanceRepo;
    @Autowired
    NpcInstanceRepositoryI npcInstanceRepositoryI;

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
    public Dungeon createDungeon(String AName, User AUser, Status AStatus) {
        dungeonDesigner = AUser;
        dungeon = new Dungeon(AName, dungeonDesigner.getUserId());
        dungeon.setDungeonStatus(AStatus);
        AUser.addDungeon(dungeon);
        dungeonRepo.save(dungeon);
        return dungeon;
    }

    @Override
    public void setStartRoom(Room ARoom) {
        dungeon.setStartRoomId(ARoom.getRoomId());
        dungeonRepo.save(dungeon);
    }

    @Override
    public char getCommandSymbol() {
        if (dungeon.getCommandSymbol() == null) {
            dungeon.setCommandSymbol('/');
        }
        return dungeon.getCommandSymbol();
    }

    @Override
    public void setCommandSymbol(char ACommandSymbol) {
        dungeon.setCommandSymbol(ACommandSymbol);
        dungeonRepo.save(dungeon);
    }

    @Override
    public void setMaxPlayercount(int ACount) {

    }

    @Override
    public List<Room> getAllDungeonRooms() {
        return dungeon.getRooms();
    }

    @Override
    public void createItem(String AName, ItemType AType, String ADescription, Long ASize) {
        Item item = new Item(AName, ASize, ADescription);
        item.setType(AType);
        dungeon.addItem(item);
        itemRepo.save(item);
        //dungeonRepo.save(dungeon);
    }

    @Override
    public void updateItem(Item AItem) {
        itemRepo.save(AItem);
    }

    @Override
    public void deleteItem(Item AItem) {
        dungeon.removeItem(AItem);
        //dungeonRepo.save(dungeon);
        itemRepo.delete(AItem);
    }

    @Override
    public Item getItem(Long AItemId) {
        return null;
    }

    @Override
    public void createNPC(String AName, String ADescription, Race ARace) {
        NPC newNPC = new NPC(AName, ARace, ADescription);
        dungeon.addNpc(newNPC);
        npcRepo.save(newNPC);
        //dungeonRepo.save(dungeon);
    }

    @Override
    public void updateNPC(NPC ANPC) {
        npcRepo.save(ANPC);
    }

    @Override
    public void deleteNPC(NPC ANPC) {
        dungeon.removeNpc(ANPC);
        //dungeonRepo.save(dungeon);
        npcRepo.delete(ANPC);
    }

    @Override
    public Item getNPC(Long ANPCId) {
        return null;
    }

    @Override
    public void createRole(String AName, String ADescription, Long ALifepointsBonus) {
        Role newRole = new Role(AName, ADescription, ALifepointsBonus);
        dungeon.addRole(newRole);
        roleRepo.save(newRole);
        //dungeonRepo.save(dungeon);
    }

    @Override
    public void removeRole(Role ARole) {
        dungeon.removeRole(ARole);
        roleRepo.delete(ARole);
        //dungeonRepo.save(dungeon);
    }

    @Override
    public List<Role> getAllRoles() {
        return dungeon.getRoles();
    }

    @Override
    public void createRace(String AName, String ADescription, Long ALifepointsBonus) {
        Race newRace = new Race(AName, ADescription,ALifepointsBonus);
        dungeon.addRace(newRace);
        raceRepo.save(newRace);
        //dungeonRepo.save(dungeon);
    }

    @Override
    public void removeRace(Race ARace) {
        dungeon.removeRace(ARace);
        raceRepo.delete(ARace);
        //dungeonRepo.save(dungeon);

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
        //dungeonRepo.save(dungeon);
    }


    @Override
    public void setNeighborRoom(Direction ADirection, Long ARoomId, Long ANeighborRoom) {

    }

    @Override
    public void removeNeighborRoom(String ADirection) {

    }

    @Override
    public double getNumberOfItem(Room ARoom, Item AItem) {
        double counter = 0;
        for (ItemInstance itemInstance : getAllItems(ARoom)) {
            if (itemInstance.getItem().getItemId().equals(AItem.getItemId())) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public void setItemInstances(Room ARoom, List<ItemInstance> AItemList) {
        ARoom.getItems()
                .clear();
        for (ItemInstance myItem : itemInstanceRepo.findByRoom(ARoom)) {
            itemInstanceRepo.delete(myItem);
        }
        for (ItemInstance myItem : AItemList) {
            myItem.setRoom(ARoom);
            itemInstanceRepo.save(myItem);
        }
        ARoom.getItems()
                .addAll(AItemList);
        try {
            roomRepo.save(ARoom);
        } catch (Exception e) {

            for (ItemInstance myItem : AItemList) {
                myItem.setRoom(ARoom);
                itemInstanceRepo.save(myItem);
            }
            System.out.println("FALSCH");
            //Notification.show("Hier findet er den Entity nicht!");
        }
    }

    @Override
    public double getNumberOfNPC(Room ARoom, NPC ANPC) {
        double counter = 0;
        for (NpcInstance npcInstance : getAllNPCs(ARoom)) {
            if (npcInstance.getNpc().getNpcId().equals(ANPC.getNpcId())) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public void setNpcInstances(Room ARoom, List<NpcInstance> ANPCList) {
        ARoom.getItems()
                .clear();
        for (NpcInstance myNpc : npcInstanceRepositoryI.findByRoom(ARoom)) {
            npcInstanceRepositoryI.delete(myNpc);
        }
        for (NpcInstance myNpc : ANPCList) {
            myNpc.setRoom(ARoom);
            npcInstanceRepositoryI.save(myNpc);
        }
        ARoom.getNpcs()
                .addAll(ANPCList);
        try {
            roomRepo.save(ARoom);
        } catch (Exception e) {

            for (NpcInstance myNpc : ANPCList) {
                myNpc.setRoom(ARoom);
                npcInstanceRepositoryI.save(myNpc);
            }
            System.out.println("FALSCH");
            //Notification.show("Hier findet er den Entity nicht!");
        }
    }

    @Override
    public List<Item> getAllItems() {

        return dungeon.getItems();
    }

    @Override
    public List<ItemInstance> getAllItems(Room ARoom) {
        return itemInstanceRepo.findByRoom(ARoom);
    }

    @Override
    public List<NPC> getAllNPCs() {
        return dungeon.getNpcs();
    }

    @Override
    public List<NpcInstance> getAllNPCs(Room ARoom){
        return npcInstanceRepositoryI.findByRoom(ARoom);
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
        for (Room r : dungeon.getRooms()) {
            if(r.getRoomId().equals(ARoomID)){
                return r;
            }
        }
        return null;
    }

    @Override
    public void addRoom(Room ARoom) {
        //wenn der Raum existiert, wird er überschrieben, wenn nicht, wird ein neuer Raum in die Datenbank gespeichert
        //Raum dem aktuellen Dungeon hinzufügen
        dungeon.addRoom(ARoom);
        roomRepo.save(ARoom);
        //geupdateten Dungeon in die Datenbank speichern
    }

    @Override
    public void saveRoom(Room ARoom) {
        roomRepo.save(ARoom);
        //TODO test: dungeonRepo.save(dungeon);
    }

    @Override
    public void addItemInstance(ItemInstance AInstance) {
        itemInstanceRepo.save(AInstance);
    }

    @Override
    public List<User> getAllUsers()
    {

        return userRepo.findAll();
    }

    @Override
    public User getUser(String AName)
    {

       return userRepo.findByName(AName);
    }


    public void saveUser(User AUser) {
        userRepo.save(AUser);
    }


}
