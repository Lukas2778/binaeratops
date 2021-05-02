package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.ItemI;
import de.dhbw.binaeratops.model.enums.ItemType;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entity Objekt für einen Gegenstand.
 * <p>
 * Es repräsentiert die Entity "Gegenstand" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Gegenstand Schnittstelle.
 * <p>
 * @see ItemI
 *
 * @author Nicolas Haug
 */
@Entity
public class Item implements ItemI{

    @Id
    @GeneratedValue
    private Long itemId;

    private String itemName;

    private Long size;

    private String description;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @ManyToOne
    private Dungeon dungeon;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Avatar inventoryAvatar;

    @ManyToOne
    private Avatar equipmentAvatar;

    @ManyToOne
    private NPC npc;

    /**
     * Konstruktor zum Erzeugen eines Gegenstandes mit allen Eigenschaften.
     *
     * @param AName Name des Gegenstandes.
     * @param ASize Größe des Gegenstandes.
     * @param ADescription Beschreibung des Gegenstandes.
     */
    public Item(String AName, Long ASize, String ADescription) {
        this.itemName = AName;
        this.size = ASize;
        this.description = ADescription;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines Gegenstandes.
     */
    public Item() {

    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long AItemId) {
        this.itemId = AItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String AItemName) {
        this.itemName = AItemName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long ASize) {
        this.size = ASize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String ADescription) {
        this.description = ADescription;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType AType) {
        this.type = AType;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Avatar getInventoryAvatar() {
        return inventoryAvatar;
    }

    public void setInventoryAvatar(Avatar inventoryAvatar) {
        this.inventoryAvatar = inventoryAvatar;
    }

    public Avatar getEquipmentAvatar() {
        return equipmentAvatar;
    }

    public void setEquipmentAvatar(Avatar equipmentAvatar) {
        this.equipmentAvatar = equipmentAvatar;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof Item) {
            Item other = (Item) AOther;
            equals = (itemId == other.itemId);
            // && (name == other.name || (name != null &&
            //                    name.equalsIgnoreCase(other.name)))
        }

        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Item[ID = ")
                .append(itemId)
                .append(" | Name = ")
                .append(itemName)
                .append(" | Größe = ")
                .append(size)
                .append(" | Typ = ")
                .append(type)
                .append(" | Beschreibung = ")
                .append(description)
                .append("]\n");
        return s.toString();
    }

    /**
     * Prüft die Gültigkeit eines Objekts und konvertiert das API-Interface zur
     * erwarteten Klasse der Implementation.
     *
     * @param AItem Objekt.
     * @return Objekt.
     * @throws InvalidImplementationException Objekt ungültig.
     */
    public static Item check(ItemI AItem) throws InvalidImplementationException {
        if (!(AItem instanceof Item)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            Item.class, AItem.getClass()));
        }

        return (Item) AItem;
    }
}
