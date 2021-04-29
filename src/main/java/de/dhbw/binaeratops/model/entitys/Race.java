package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.RaceI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;

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

    /**
     * Konstruktor zum Erzeugen einer Rasse mit allen Eigenschaften.
     *
     * @param ARaceName Name der Rasse.
     * @param ADescription Beschreibung der Rasse.
     */
    public Race(String ARaceName, String ADescription) {
        this.raceName = ARaceName;
        this.description = ADescription;
    }

    /**
     * Standardkonstruktor zum Erzeugen einer Rasse.
     */
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

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof Race) {
            Race other = (Race) AOther;
            equals = (raceId == other.raceId);
            // && (name == other.name || (name != null &&
            //                    name.equalsIgnoreCase(other.name)))
        }

        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(raceId);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Rasse[ID = ")
                .append(raceId)
                .append(" | Name = ")
                .append(raceName)
                .append(" | Beschreibung = ")
                .append(description)
                .append("]\n");
        return s.toString();
    }

    /**
     * Prüft die Gültigkeit eines Objekts und konvertiert das API-Interface zur
     * erwarteten Klasse der Implementation.
     *
     * @param ARace Objekt.
     * @return Objekt.
     * @throws InvalidImplementationException Objekt ungültig.
     */
    public static Race check(RaceI ARace) throws InvalidImplementationException {
        if (!(ARace instanceof Race)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            Race.class, ARace.getClass()));
        }

        return (Race) ARace;
    }
}
