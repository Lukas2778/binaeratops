package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.RaceI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity Objekt für eine Rasse.
 * <p>
 * Es repräsentiert die Entity "Rasse" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Rasse Schnittstelle.
 * <p>
 * @see RaceI
 *
 * @author Nicolas Haug
 */
@Entity
public class Race implements RaceI {

    @Id
    @GeneratedValue
    private Long raceId;

    private String raceName;

    private String description;

    public Race(String ARaceName, String ADescription) {
        this.raceName = ARaceName;
        this.description = ADescription;
    }
    public Race() {

    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long ARaceId) {
        this.raceId = ARaceId;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String ARaceName) {
        this.raceName = ARaceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String ADescription) {
        this.description = ADescription;
    }
}
