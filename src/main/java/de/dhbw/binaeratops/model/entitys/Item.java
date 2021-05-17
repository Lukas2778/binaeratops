package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.ItemI;
import de.dhbw.binaeratops.model.enums.ItemType;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entity Objekt für eine Gegenstand-Blaupause.
 * <p>
 * Es repräsentiert die Entity "Gegenstand" der Datenbank in der Programmlogik.
 * <p>
 * Es stellt eine Blaupause für die Entität "Gegenstand-Instanz" dar.
 *
 * @author Nicolas Haug
 * @see de.dhbw.binaeratops.model.api.ItemInstanceI
 * @see ItemI
 */
@Entity
public class Item implements ItemI {

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

    /**
     * Konstruktor zum Erzeugen eines Gegenstandes mit allen Eigenschaften.
     *
     * @param AName        Name des Gegenstandes.
     * @param ASize        Größe des Gegenstandes.
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

    @Override
    public Long getItemId() {
        return itemId;
    }

    @Override
    public void setItemId(Long AItemId) {
        this.itemId = AItemId;
    }

    @Override
    public String getItemName() {
        return itemName;
    }

    @Override
    public void setItemName(String AItemName) {
        this.itemName = AItemName;
    }

    @Override
    public Long getSize() {
        return size;
    }

    @Override
    public void setSize(Long ASize) {
        this.size = ASize;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String ADescription) {
        this.description = ADescription;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    @Override
    public void setType(ItemType AType) {
        this.type = AType;
    }

    @Override
    public Dungeon getDungeon() {
        return dungeon;
    }

    @Override
    public void setDungeon(Dungeon ADungeon) {
        this.dungeon = ADungeon;
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
