package de.dhbw.binaeratops.service.impl.parser.gamectrl;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.repository.AvatarRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.api.parser.GameCtrlCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerInsufficientPermissionException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Callbacks des Scanners für die Spielsteuerungs-Befehle.
 * <p>
 * Für Schnittstelle siehe @{@link GameCtrlCmdHooksI}.
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
            return new UserMessage("view.game.ctrl.cmd.whoami", avatar.getName(), race.getRaceName(), race.getDescription(), role.getRoleName(), role.getDescription() /*, TODO Lebenspunkte hinzufügen*/);
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
                        String.valueOf(dungeon.getCurrentUsers().size()), getCurrentUsers(dungeon), currentRoom.getRoomName(),
                        currentRoom.getDescription(), String.valueOf(avatar.getVisitedRooms().size()), String.valueOf(dungeon.getRooms().size()));
            } else {
                return new UserMessage("view.game.ctrl.cmd.info.all", dungeon.getDungeonName(), dungeonMaster.getName(),
                        String.valueOf(dungeon.getCurrentUsers().size()), getCurrentUsers(dungeon), currentRoom.getRoomName(),
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
            User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
            Room currentRoom = avatar.getCurrentRoom();
            return new UserMessage("view.game.ctrl.cmd.info.players", String.valueOf(dungeon.getCurrentUsers().size()),
                    getCurrentUsers(dungeon));
        } else {
            throw new CmdScannerInsufficientPermissionException("INFO PLAYERS");
        }
    }

    @Override
    public UserMessage onMoveNorth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        // TODO Prüfen, das nicht bereits eine Request abgeschickt wurde.
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
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
            throw new CmdScannerInsufficientPermissionException("MOVE NORTH");
        }
    }

    @Override
    public UserMessage onMoveEast(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        // TODO Prüfen, das nicht bereits eine Request abgeschickt wurde.
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            // Bewegen nach Norden, sofern Raum existent ist.
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
            throw new CmdScannerInsufficientPermissionException("MOVE EAST");
        }
    }

    @Override
    public UserMessage onMoveSouth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        // TODO Prüfen, das nicht bereits eine Request abgeschickt wurde.
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            // Bewegen nach Norden, sofern Raum existent ist.
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
            throw new CmdScannerInsufficientPermissionException("MOVE SOUTH");
        }
    }

    @Override
    public UserMessage onMoveWest(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        // TODO Prüfen, das nicht bereits eine Request abgeschickt wurde.
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
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
    public UserMessage onExamine(DungeonI ADungeon, String AItemOrNpc, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onShowInventory(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onShowEquipment(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onTake(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onDrop(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onEat(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onDrink(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onEquip(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onLayDown(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onGetHealth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onTalk(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String ANpcName, String AMessage) throws CmdScannerException {
        return null;
    }

    private String getCurrentUsers(Dungeon ADungeon) {
        List<Avatar> avatars = new ArrayList<>(); // TODO Produktiv-Liste einsetzen...
        StringBuilder s = new StringBuilder();
        s.append("<ol>");
        for (Avatar tempAvatar : avatars) {
            s.append("<li>").append(tempAvatar.getName()).append("</li>");
        }
        s.append("</ol>");
        return s.toString();
    }

    private String getNpcs(Avatar AAvatar) {
        StringBuilder s = new StringBuilder();
        for (NPC npc : AAvatar.getCurrentRoom().getNpcs()) {
            s.append(npc.getNpcName());
        }
        return s.toString();
    }

    private String getItems(Avatar AAvatar) {
        StringBuilder s = new StringBuilder();
        for (ItemInstance item : AAvatar.getCurrentRoom().getItems()) {
            s.append(item.getItem().getItemName());
        }
        return s.toString();
    }
}
