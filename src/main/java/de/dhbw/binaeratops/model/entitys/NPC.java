package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entity Objekt für einen NPC.
 * <p>
 * Es repräsentiert die Entity "NPC" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der NPC Schnittstelle.
 * <p>
 *
 * @author Nicolas Haug
 * @see de.dhbw.binaeratops.model.api.NPCI
 */
@Entity
public class NPC  {

    @Id
    @GeneratedValue
    private Long npcId;

    private String npcName;

    @OneToOne
    private Race race;

    private String description;

    @ManyToOne
    private Dungeon dungeon;

    @OneToMany(mappedBy = "npc", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemInstance> luggage = new ArrayList<>();


    /**
     * Konstruktor zum Erzeugen eines NPCs mit allen Eigenschaften.
     *
     * @param ANpcName     Name des NPCs.
     * @param ARace        Rasse des NPCs.
     * @param ADescription Beschreibung des NPCs.
     */
    public NPC(String ANpcName, Race ARace, String ADescription) {
        this.npcName = ANpcName;
        this.race = ARace;
        this.description = ADescription;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines NPCs.
     */
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

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon ADungeon) {
        this.dungeon = ADungeon;
    }

    public List<ItemInstance> getLuggage() {
        return luggage;
    }






    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof NPC) {
            NPC other = (NPC) AOther;
            equals = (npcId == other.npcId);
            // && (name == other.name || (name != null &&
            //                    name.equalsIgnoreCase(other.name)))
        }

        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(npcId);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("NPC[ID = ")
                .append(npcId)
                .append(" | Name = ")
                .append(npcName)
                .append(" | Rasse = ")
                .append(race.getRaceName())
                .append(" | Beschreibung = ")
                .append(description)
                .append("]\n");
        return s.toString();
    }

    /**
     * Prüft die Gültigkeit eines Objekts und konvertiert das API-Interface zur
     * erwarteten Klasse der Implementation.
     *
     * @param ANpc Objekt.
     * @return Objekt.
     * @throws InvalidImplementationException Objekt ungültig.
     */
    public static NPC check(NPC ANpc) throws InvalidImplementationException {
        if (!(ANpc instanceof NPC)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            NPC.class, ANpc.getClass()));
        }

        return (NPC) ANpc;
    }

}
