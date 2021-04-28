package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.enums.ItemType;

/**
 * TODO Kommentar schreiben
 */
public interface ItemI {

    Long getItemId();

    void setItemId(Long AItemId);

    String getItemName();

    void setItemName(String AItemName);

    Long getSize();

    void setSize(Long ASize);

    String getDescription();

    void setDescription(String ADescription);

    ItemType getType();

    void setType(ItemType AType);
}
