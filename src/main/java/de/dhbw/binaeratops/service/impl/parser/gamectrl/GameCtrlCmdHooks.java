package de.dhbw.binaeratops.service.impl.parser.gamectrl;

import de.dhbw.binaeratops.model.entitys.UserAction;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.repository.*;
import de.dhbw.binaeratops.service.api.parser.GameCtrlCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.*;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.UnicastProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Callbacks des Scanners für die Spielsteuerungs-Befehle.
 * <p>
 * Für Schnittstelle siehe {@link GameCtrlCmdHooksI}.
 * </p>
 *
 * @author Nicolas Haug, Lukas Göpel
 */
@Scope(value = "session")
@Service
public class GameCtrlCmdHooks implements GameCtrlCmdHooksI {

    @Autowired
    UserRepositoryI userRepo;

    @Autowired
    RoomRepositoryI roomRepo;

    @Autowired
    AvatarRepositoryI avatarRepo;

    @Autowired
    ItemInstanceRepositoryI itemInstanceRepo;

    @Autowired
    UserActionRepositoryI userActionRepo;

    @Autowired
    UnicastProcessor<UserAction> userActionPublisher;

    @Override
    public UserMessage onWhereAmI(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
            return new UserMessage("view.game.ctrl.cmd.whereami", avatar.getCurrentRoom().getRoomName(), dungeon.getDungeonName(), dungeonMaster.getName(), avatar.getCurrentRoom().getDescription());
        } else {
            throw new CmdScannerInsufficientPermissionException("WHEREAMI");
        }
    }

    @Override
    public UserMessage onWhoAmI(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            Race race = avatar.getRace();
            Role role = avatar.getRole();
            return new UserMessage("view.game.ctrl.cmd.whoami", avatar.getName(), race.getRaceName(), race.getDescription(), role.getRoleName(), role.getDescription(), String.valueOf(avatar.getLifepoints()));
        } else {
            throw new CmdScannerInsufficientPermissionException("WHOAMI");
        }
    }

    @Override
    public UserMessage onInfoAll(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
            Room currentRoom = avatar.getCurrentRoom();
            boolean alldiscorved = false;
            if (avatar.getVisitedRooms().size() == dungeon.getRooms().size()) {
                alldiscorved = true;
            }
            if (alldiscorved) {
                return new UserMessage("view.game.ctrl.cmd.info.all", dungeon.getDungeonName(), dungeonMaster.getName(),
                        String.valueOf(dungeon.getCurrentUsers().size()), getCurrentAvatars(dungeon), currentRoom.getRoomName(),
                        currentRoom.getDescription(), String.valueOf(avatar.getVisitedRooms().size()), String.valueOf(dungeon.getRooms().size()));
            } else {
                return new UserMessage("view.game.ctrl.cmd.info.all", dungeon.getDungeonName(), dungeonMaster.getName(),
                        String.valueOf(dungeon.getCurrentUsers().size()), getCurrentAvatars(dungeon), currentRoom.getRoomName(),
                        currentRoom.getDescription(), String.valueOf(avatar.getVisitedRooms().size()), "???");
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("INFO ALL");
        }
    }

    @Override
    public UserMessage onInfoRoom(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            Room currentRoom = avatar.getCurrentRoom();
            boolean alldiscorved = false;
            if (avatar.getVisitedRooms().size() == dungeon.getRooms().size()) {
                alldiscorved = true;
            }
            if (alldiscorved) {
                return new UserMessage("view.game.ctrl.cmd.info.room", currentRoom.getRoomName(),
                        currentRoom.getDescription(), String.valueOf(avatar.getVisitedRooms().size()), String.valueOf(dungeon.getRooms().size()));
            } else {
                return new UserMessage("view.game.ctrl.cmd.info.room", currentRoom.getRoomName(),
                        currentRoom.getDescription(), String.valueOf(avatar.getVisitedRooms().size()), "???");
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("INFO ROOM");
        }
    }

    @Override
    public UserMessage onInfoPlayers(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            return new UserMessage("view.game.ctrl.cmd.info.players", String.valueOf(dungeon.getCurrentUsers().size()),
                    getCurrentAvatars(dungeon));
        } else {
            throw new CmdScannerInsufficientPermissionException("INFO PLAYERS");
        }
    }

    @Override
    public UserMessage onMoveNorth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) { // Sofern noch keine Anfrage gestellt
                // Bewegen nach Norden, sofern Raum existent ist.
                Long northNeighboor = avatar.getCurrentRoom().getNorthRoomId();
                if (northNeighboor != null) {
                    Room north = roomRepo.findByRoomId(northNeighboor);
                    avatar.setCurrentRoom(north);
                    avatarRepo.save(avatar);
                    return new UserMessage("view.game.ctrl.cmd.move.north");
                } else {
                    return new UserMessage("view.game.ctrl.cmd.move.invalid");
                }
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("MOVE NORTH");
        }
    }

    @Override
    public UserMessage onMoveEast(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) { // Sofern noch keine Anfrage gestellt
                // Bewegen nach Osten, sofern Raum existent ist.
                Long eastNeighboorId = avatar.getCurrentRoom().getEastRoomId();
                if (eastNeighboorId != null) {
                    Room east = roomRepo.findByRoomId(eastNeighboorId);
                    avatar.setCurrentRoom(east);
                    avatarRepo.save(avatar);
                    return new UserMessage("view.game.ctrl.cmd.move.east");
                } else {
                    return new UserMessage("view.game.ctrl.cmd.move.invalid");
                }
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("MOVE EAST");
        }
    }

    @Override
    public UserMessage onMoveSouth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) { // Sofern noch keine Anfrage gestellt
                // Bewegen nach Süden, sofern Raum existent ist.
                Long southNeighboorId = avatar.getCurrentRoom().getSouthRoomId();
                if (southNeighboorId != null) {
                    Room south = roomRepo.findByRoomId(southNeighboorId);
                    avatar.setCurrentRoom(south);
                    avatarRepo.save(avatar);
                    return new UserMessage("view.game.ctrl.cmd.move.south");
                } else {
                    return new UserMessage("view.game.ctrl.cmd.move.invalid");
                }
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("MOVE SOUTH");
        }
    }

    @Override
    public UserMessage onMoveWest(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) { // Sofern noch keine Anfrage gestellt
                // Bewegen nach Norden, sofern Raum existent ist.
                Long westNeighboorId = avatar.getCurrentRoom().getWestRoomId();
                if (westNeighboorId != null) {
                    Room west = roomRepo.findByRoomId(westNeighboorId);
                    avatar.setCurrentRoom(west);
                    avatarRepo.save(avatar);
                    return new UserMessage("view.game.ctrl.cmd.move.west");
                } else {
                    return new UserMessage("view.game.ctrl.cmd.move.invalid");
                }
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("MOVE WEST");
        }
    }

    @Override
    public UserMessage onLookAround(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            Long northRoomId = avatar.getCurrentRoom().getNorthRoomId();
            Long eastRoomId = avatar.getCurrentRoom().getEastRoomId();
            Long southRoomId = avatar.getCurrentRoom().getSouthRoomId();
            Long westRoomId = avatar.getCurrentRoom().getWestRoomId();
            boolean north = northRoomId != null;
            boolean south = southRoomId != null;
            boolean west = westRoomId != null;
            boolean east = eastRoomId != null;
            boolean roomHasNpcs = avatar.getCurrentRoom().getNpcs().size() != 0;
            boolean roomHasItems = avatar.getCurrentRoom().getItems().size() != 0;
            if (north && east && south && west) { //NESW
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.s.w.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.s.w.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.s.w.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.s.w");
                }
            } else if (north && east && south && westRoomId == null) { //NES
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.s.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.s.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.s.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.s");
                }
            } else if (north && east && southRoomId == null && west) { //NEW
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.w.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.w.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.w.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.w");
                }
            } else if (north && !east && south && west) { //NSW
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.s.w.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.s.w.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.s.w.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.s.w");
                }
            } else if (!north && east && south && west) { //ESW
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.s.w.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.s.w.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.s.w.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.s.w");
                }
            } else if (north && !east && !south && !west) { //N
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n");
                }
            } else if (!north && east && !south && !west) { //E
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e");
                }
            } else if (!north && !east && south && !west) { //S
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.s.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.s.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.s.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.s");
                }
            } else if (!north && !east && !south && west) { //W
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.w.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.w.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.w.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.w");
                }
            } else if (north && east && !south && !west) { //NE
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.e");
                }
            } else if (north && !east && south && !west) { //NS
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.s.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.s.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.s.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.s");
                }
            } else if (north && !east && !south && west) { //NW
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.w.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.w.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.w.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.n.w");
                }
            } else if (!north && east && south && !west) { //ES
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.s.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.s.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.s.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.s");
                }
            } else if (!north && !east && south && west) { //SW
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.s.w.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.s.w.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.s.w.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.s.w");
                }
            } else if (!north && east && !south && west) { //EW
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.w.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.w.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.w.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around.e.w");
                }
            } else { //Weder noch
                if (roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.npc.item", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar), String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else if (roomHasNpcs && !roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.npc", String.valueOf(avatar.getCurrentRoom().getNpcs().size()),
                            getNpcs(avatar));
                } else if (!roomHasNpcs && roomHasItems) {
                    return new UserMessage("view.game.ctrl.cmd.look.around.item", String.valueOf(avatar.getCurrentRoom().getItems().size()), getItems(avatar));
                } else {
                    return new UserMessage("view.game.ctrl.cmd.look.around");
                }
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("LOOK AROUND");
        }
    }

    @Override
    public UserMessage onExamineNpc(DungeonI ADungeon, String ANpc, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            List<NPCInstance> npcs = avatar.getCurrentRoom().getNpcs();
            for (NPCInstance npc : npcs) {
                if (npc.getNpc().getNpcName().equalsIgnoreCase(ANpc)) {
                    return new UserMessage("view.game.ctrl.cmd.examine.npc", npc.getNpc().getNpcName(), npc.getNpc().getDescription());
                }
            }
            // Wurde nicht gefunden in der Liste
            throw new CmdScannerInvalidParameterException(ANpc);
        } else {
            throw new CmdScannerInsufficientPermissionException("EXAMINE NPC");
        }
    }

    @Override
    public UserMessage onExamineItem(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            List<ItemInstance> items = avatar.getCurrentRoom().getItems();
            for (ItemInstance item : items) {
                if (item.getItem().getItemName().equalsIgnoreCase(AItem)) {
                    return new UserMessage("view.game.ctrl.cmd.examine.item", item.getItem().getItemName(), item.getItem().getDescription());
                }
            }
            // Wurde nicht gefunden in der Liste
            throw new CmdScannerInvalidParameterException(AItem);
        } else {
            throw new CmdScannerInsufficientPermissionException("EXAMINE ITEM");
        }
    }

    @Override
    public UserMessage onShowInventory(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (avatar.getInventory().size() == 0) {
                return new UserMessage("view.game.ctrl.cmd.show.inventory.empty");
            } else {
                return new UserMessage("view.game.ctrl.cmd.show.inventory", String.valueOf(avatar.getInventory().size()),
                        String.valueOf(getInventorySize(avatar)), String.valueOf(dungeon.getDefaultInventoryCapacity()),
                        getInventory(avatar));
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("SHOW INVENTORY");
        }
    }

    @Override
    public UserMessage onShowEquipment(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (avatar.getEquipment().size() == 0) {
                return new UserMessage("view.game.ctrl.cmd.show.equipment.empty");
            } else {
                return new UserMessage("view.game.ctrl.cmd.show.equipment", getEquipment(avatar));
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("SHOW INVENTORY");
        }
    }

    @Override
    public UserMessage onTake(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) {
                for (ItemInstance item : avatar.getCurrentRoom().getItems()) {
                    if (item.getItem().getItemName().equalsIgnoreCase(AItem)) {
                        Item i = item.getItem();
                        if (getInventorySize(avatar) + i.getSize() <= dungeon.getDefaultInventoryCapacity()) {
                            // Zum Inventar hinzufügen, da erlaubt
                            avatar.getCurrentRoom().removeItem(item);
                            avatar.addInventoryItem(item);
                            itemInstanceRepo.save(item);
                            avatarRepo.save(avatar);
                            return new UserMessage("view.game.ctrl.cmd.take", item.getItem().getItemName());
                        } else {
                            // Inventar voll.
                            return new UserMessage("view.game.ctrl.cmd.take.failure", String.valueOf(getInventorySize(avatar)),
                                    String.valueOf(dungeon.getDefaultInventoryCapacity()), String.valueOf(item.getItem().getSize()));
                        }
                    }
                }
                // Gegenstand wurde nicht gefunden.
                throw new CmdScannerInvalidParameterException(AItem);
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("TAKE");
        }
    }

    @Override
    public UserMessage onDrop(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) { // Sofern noch keine Anfrage gestellt
                for (ItemInstance item : avatar.getInventory()) {
                    if (item.getItem().getItemName().equalsIgnoreCase(AItem)) {
                        avatar.removeInventoryItem(item);
                        avatar.getCurrentRoom().addItem(item);
                        itemInstanceRepo.save(item);
                        roomRepo.save(avatar.getCurrentRoom());
                        avatarRepo.save(avatar);
                        return new UserMessage("view.game.ctrl.cmd.drop", item.getItem().getItemName());
                    }
                }
                // Gegenstand wurde nicht gefunden.
                throw new CmdScannerInvalidParameterException(AItem);
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("DROP");
        }
    }

    @Override
    public UserMessage onConsume(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = avatarRepo.findByAvatarId(AAvatar.getAvatarId());
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) { // Sofern noch keine Anfrage gestellt
                for (ItemInstance item : avatar.getInventory()) {
                    if (item.getItem().getItemName().equalsIgnoreCase(AItem)) {
                        Item tempItem = item.getItem();
                        avatar.removeInventoryItem(item);
                        avatar.setRequested(true);
                        itemInstanceRepo.save(item);
                        avatarRepo.save(avatar);
                        UserAction userAction = new UserAction(dungeon, avatar, ActionType.CONSUME, tempItem);
                        userActionRepo.save(userAction);
                        userActionPublisher.onNext(userAction);
                        return new UserMessage("view.game.ctrl.cmd.consume", item.getItem().getItemName());
                    }
                }
                // Gegenstand wurde nicht gefunden.
                throw new CmdScannerInvalidParameterException(AItem);
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("CONSUME");
        }
    }

    @Override
    public UserMessage onEquip(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            for (ItemInstance item : avatar.getInventory()) {
                if (item.getItem().getItemName().equalsIgnoreCase(AItem)) {
                    if (checkIfItemHasCorrectType(item)) {
                        // Wenn ItemTyp equipped werden darf.
                        if (checkIfItemTypeAlreadyInEquip(avatar, item)) {
                            // Wenn Typ schon enthalten ist
                            for (ItemInstance alreadyEquipped : avatar.getEquipment()) {
                                if (alreadyEquipped.getItem().getType().equals(item.getItem().getType())) {
                                    // Ausrüsten
                                    avatar.removeEquipmentItem(alreadyEquipped);
                                    avatar.addEquipmentItem(item);
                                    itemInstanceRepo.save(alreadyEquipped);
                                    itemInstanceRepo.save(item);
                                    avatarRepo.save(avatar);
                                    return new UserMessage("view.game.ctrl.cmd.equip.already.equipped", item.getItem().getItemName(), alreadyEquipped.getItem().getItemName());
                                }
                            }
                        } else { // Wenn noch kein Items dieses Typs darin ist.
                            avatar.addEquipmentItem(item);
                            avatar.setRequested(true);
                            itemInstanceRepo.save(item);
                            avatarRepo.save(avatar);
                            return new UserMessage("view.game.ctrl.cmd.equip", item.getItem().getItemName());
                        }
                    } else {
                        // Typ darf nicht equipped werden.
                        throw new CmdScannerInvalidItemTypeException(item.getItem().getItemName(), item.getItem().getType().toString());
                    }
                }
            }
            // Gegenstand wurde nicht gefunden.
            throw new CmdScannerInvalidParameterException(AItem);
        } else {
            throw new CmdScannerInsufficientPermissionException("EQUIP");
        }
    }

    @Override
    public UserMessage onLayDown(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) { // Sofern noch keine Anfrage gestellt
                for (ItemInstance item : avatar.getEquipment()) {
                    if (item.getItem().getItemName().equalsIgnoreCase(AItem)) {
                        // Wenn Item in Equipment
                        avatar.removeEquipmentItem(item);
                        itemInstanceRepo.save(item);
                        avatarRepo.save(avatar);
                        return new UserMessage("view.game.ctrl.cmd.laydown", item.getItem().getItemName());
                    }
                }
                // Gegenstand wurde nicht gefunden.
                throw new CmdScannerInvalidParameterException(AItem);
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("LAYDOWN");
        }
    }

    @Override
    public UserMessage onGetHealth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            return new UserMessage("view.game.ctrl.cmd.health", String.valueOf(avatar.getLifepoints()));
        } else {
            throw new CmdScannerInsufficientPermissionException("HEALTH");
        }
    }

    @Override
    public UserMessage onTalk(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String ANpcName, String AMessage) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) { // Sofern noch keine Anfrage gestellt
                for (NPCInstance npc : avatar.getCurrentRoom().getNpcs()) {
                    if (npc.getNpc().getNpcName().equalsIgnoreCase(ANpcName)) {
                        // Wenn Item in Equipment
                        avatar.setRequested(true);
                        avatarRepo.save(avatar);
                        UserAction userAction = new UserAction(dungeon, avatar, ActionType.TALK, AMessage, npc);
                        userActionRepo.save(userAction);
                        userActionPublisher.onNext(userAction);
                        return new UserMessage("view.game.ctrl.cmd.talk", npc.getNpc().getNpcName(), AMessage);
                    }
                }
                // Gegenstand wurde nicht gefunden.
                throw new CmdScannerInvalidParameterException(ANpcName);
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("TALK");
        }
    }

    @Override
    public UserMessage onHit(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String ANpcName) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            if (!avatar.hasRequested()) { // Sofern noch keine Anfrage gestellt
                for (NPCInstance npc : avatar.getCurrentRoom().getNpcs()) {
                    if (npc.getNpc().getNpcName().equalsIgnoreCase(ANpcName)) {
                        // Wenn Item in Equipment
                        avatar.setRequested(true);
                        avatarRepo.save(avatar);
                        //userActionPublisher.onNext(new UserAction(dungeon, avatar, "TALK", ANpcName, AMessage)); // TODO HIT
                        return new UserMessage("view.game.ctrl.cmd.hit", npc.getNpc().getNpcName());
                    }
                }
                // Gegenstand wurde nicht gefunden.
                throw new CmdScannerInvalidParameterException(ANpcName);
            } else {
                throw new CmdScannerAlreadyRequestedException();
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("HIT");
        }
    }

    /**
     * Gibt die NPCs im Raum des Avatars als HTML String zurück.
     *
     * @param AAvatar Avatar.
     * @return NPCs im Raum des Avatars als HTML String.
     */
    private String getNpcs(Avatar AAvatar) {
        StringBuilder s = new StringBuilder();
        int i = 0;
        for (NPCInstance npc : AAvatar.getCurrentRoom().getNpcs()) {
            s.append(npc.getNpc().getNpcName()).append(", ");
            i += npc.getNpc().getNpcName().length() + 2;
        }
        String s2 = s.toString();
        return s2.substring(0, i - 2);
    }

    /**
     * Gibt die Gegenstände im Raum des Avatars als HTML String zurück.
     *
     * @param AAvatar Avatar.
     * @return Gegenstände im Raum des Avatars als HTML String.
     */
    private String getItems(Avatar AAvatar) {
        StringBuilder s = new StringBuilder();
        int i = 0;
        for (ItemInstance item : AAvatar.getCurrentRoom().getItems()) {
            s.append(item.getItem().getItemName()).append(", ");
            i += item.getItem().getItemName().length() + 2;
        }
        String s2 = s.toString(); //s.substring(0, i-2);
        return s2.substring(0, i - 2);
    }

    /**
     * Gibt das Inventar des Avatars als HTML String zurück.
     *
     * @param AAvatar Avatar.
     * @return Inventar des Avatars als HTML String.
     */
    private String getInventory(Avatar AAvatar) {
        StringBuilder s = new StringBuilder();
        s.append("<ol>");
        for (ItemInstance item : AAvatar.getInventory()) {
            s.append("<li>").append(item.getItem().getType().toString()).append(": ")
                    .append(item.getItem().getItemName()).append("</li>");
        }
        s.append("</ol>");
        return s.toString();
    }

    /**
     * Gibt die Ausrüstung des Avatars als HTML String zurück.
     *
     * @param AAvatar Avatar.
     * @return Ausrüstung des Avatars als HTML String.
     */
    private String getEquipment(Avatar AAvatar) {
        StringBuilder s = new StringBuilder();
        s.append("<ol>");
        for (ItemInstance item : AAvatar.getEquipment()) {
            s.append("<li>").append(item.getItem().getType().toString()).append(": ")
                    .append(item.getItem().getItemName()).append("</li>");
        }
        s.append("</ol>");
        return s.toString();
    }

    /**
     * Gibt die aktuelle Inventargröße des Avatars zurück.
     *
     * @param AAvatar Avatar.
     * @return Aktuelle Inventargröße.
     */
    private int getInventorySize(Avatar AAvatar) {
        int sum = 0;
        for (ItemInstance item : AAvatar.getInventory()) {
            sum += item.getItem().getSize();
        }
        return sum;
    }

    /**
     * Überprüft ob der übergebene Gegenstand den korrekten Typ für die Ausrüstung hat.
     *
     * @param AItem Gegenstand.
     * @return Wahrheitswert, ob korrekter Typ.
     */
    private boolean checkIfItemHasCorrectType(ItemInstance AItem) {
        boolean res = false;
        switch (AItem.getItem().getType()) {
            case BOOTS:
            case HELMET:
            case WEAPON:
            case PANTS:
            case CHESTPLATE:
                res = true;
                break;
            case DEFAULT:
            case CONSUMABLE:
                res = false;
                break;
        }
        return res;
    }

    /**
     * Überprüft ob der übergebene Gegenstand bereits ausgerüstet ist durch den Avatar.
     *
     * @param AAvatar Avatar.
     * @param AItem   Gegenstand.
     * @return Wahrheitswert.
     */
    private boolean checkIfItemTypeAlreadyInEquip(Avatar AAvatar, ItemInstance AItem) {
        boolean res = false;
        for (ItemInstance item : AAvatar.getEquipment()) {
            if (item.getItem().getType().equals(AItem.getItem().getType())) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Gibt alle aktuellen Avatare des Dungeons zurück.
     *
     * @param ADungeon Dungeon.
     * @return Alle aktuellen Avatare des Dungeons.
     */
    private String getCurrentAvatars(Dungeon ADungeon) {
        List<Avatar> avatars = new ArrayList<>();
        ADungeon.getAvatars().forEach(avatar -> {
            if (avatar.isActive()) {
                avatars.add(avatar);
            }
        });
        StringBuilder s = new StringBuilder();
        s.append("<ol>");
        for (Avatar tempAvatar : avatars) {
            s.append("<li>").append(tempAvatar.getName()).append("</li>");
        }
        s.append("</ol>");
        return s.toString();
    }
}
