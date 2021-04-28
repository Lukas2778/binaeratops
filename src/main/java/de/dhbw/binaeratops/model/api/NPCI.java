package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.Race;

import java.util.List;

public interface NPCI {

    Long getNpcId();

    void setNpcId(Long ANpcId);

    String getNpcName();

    void setNpcName(String ANpcName);

    Race getRace();

    void setRace(Race ARace);

    String getDescription();

    void setDescription(String ADecription);

    List<Item> getLuggage();
}
