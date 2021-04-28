package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.ItemI;
import de.dhbw.binaeratops.model.enums.ItemType;

import javax.persistence.*;

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
public class Item implements ItemI {

    @Id
    @GeneratedValue
    private Long itemId;

    private String itemName;

    private Long size;

    private String description;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    public Item(String AName, Long ASize, String ADescription) {

    }

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
}
