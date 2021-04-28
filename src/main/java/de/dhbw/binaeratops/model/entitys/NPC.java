package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.NPCI;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NPC implements NPCI {

    @Id
    @GeneratedValue
    private Long npcId;

    private String npcName;

    @OneToOne
    private Race race;

    private String description;

    @OneToMany
    private final List<Item> luggage = new ArrayList<>();

    public NPC(String ANpcName, Race ARace, String ADescription) {
        this.npcName = ANpcName;
        this.race = ARace;
        this.description = ADescription;
    }

    public NPC() {

    }

    public Long getNpcId() {
        return npcId;
    }

    public void setNpcId(Long ANpcId) {
        this.npcId = ANpcId;
    }

    public String getNpcName() {
        return npcName;
    }

    public void setNpcName(String ANpcName) {
        this.npcName = ANpcName;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race ARace) {
        this.race = ARace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String ADecription) {
        this.description = ADecription;
    }

    public List<Item> getLuggage() {
        return luggage;
    }
}
