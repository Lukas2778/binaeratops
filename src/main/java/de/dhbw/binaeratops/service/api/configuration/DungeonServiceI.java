package de.dhbw.binaeratops.service.api.configuration;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;

import java.util.List;

public interface DungeonServiceI {
    public List<Dungeon> getAllDungeonsFromUser(User AUser);
}
