package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.ItemI;
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
public class ItemInstance {

    @Id
    @GeneratedValue
    private Long itemInstanceId;

    @ManyToOne
    private Item item;

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
     * @param AItem Die erstellte Instanz ist von AItem.
     */
    public ItemInstance(Item AItem) {
        this.item = AItem;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines Gegenstandes.
     */
    public ItemInstance() {

    }

    public Long getItemInstanceId() {
        return itemInstanceId;
    }

    public void setItemInstanceId(Long AItemId) {
        this.itemInstanceId = AItemId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item AItem) {
        this.item = AItem;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room ARoom) {
        this.room = ARoom;
    }

    public Avatar getInventoryAvatar() {
        return inventoryAvatar;
    }

    public void setInventoryAvatar(Avatar AInventoryAvatar) {
        this.inventoryAvatar = AInventoryAvatar;
    }

    public Avatar getEquipmentAvatar() {
        return equipmentAvatar;
    }

    public void setEquipmentAvatar(Avatar AEquipmentAvatar) {
        this.equipmentAvatar = AEquipmentAvatar;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC ANpc) {
        this.npc = ANpc;
    }

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof ItemInstance) {
            ItemInstance other = (ItemInstance) AOther;
            equals = (itemInstanceId == other.itemInstanceId);
            // && (name == other.name || (name != null &&
            //                    name.equalsIgnoreCase(other.name)))
        }

        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemInstanceId);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Item[ID = ")
                .append(itemInstanceId)
                .append(" | Name = ")
                .append(item.getItemName())
                .append(" | Größe = ")
                .append(item.getSize())
                .append(" | Typ = ")
                .append(item.getType())
                .append(" | Beschreibung = ")
                .append(item.getDescription())
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
    public static ItemInstance check(ItemInstance AItem) throws InvalidImplementationException {
        if (!(AItem instanceof ItemInstance)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            Item.class, AItem.getClass()));
        }

        return (ItemInstance) AItem;
    }
}
