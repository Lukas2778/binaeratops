package de.dhbw.binaeratops.model.map;

import java.util.Objects;

/**
 * Klasse für ein Tupel.
 * <p>
 * Stellt alle Funktionalitäten für den Umgang mit einem Tupel der Karte bereit.
 * <p>
 * Es besteht aus einem Schlüssel und einem Wert.
 *
 * @param <object> Art des Tupels.
 * @author Matthias Rall, Nicolas Haug
 */
public class Tuple<object> {

    object key;
    object value;

    /**
     * Konstruktor zum Erzeugen eines Tupels.
     *
     * @param AKey   Schlüssel des Tupels.
     * @param AValue Wert des Tupels.
     */
    public Tuple(object AKey, object AValue) {
        this.key = AKey;
        this.value = AValue;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines Tupels.
     */
    public Tuple() {
    }

    /**
     * Gibt den Schlüssel des Tupels zurück.
     *
     * @return Schlüssel des Tupels.
     */
    public object getKey() {
        return key;
    }

    /**
     * Setzt den Schlüssel des Tupels.
     *
     * @param AKey Schlüssel des Tupels.
     */
    public void setKey(object AKey) {
        this.key = AKey;
    }

    /**
     * Gibt den Wert des Tupels zurück.
     *
     * @return Wert des Tupels.
     */
    public object getValue() {
        return value;
    }

    /**
     * Setzt den Wert des Tupels.
     *
     * @param AValue Wert des Tupels.
     */
    public void setValue(object AValue) {
        this.value = AValue;
    }

    @Override
    public boolean equals(Object AObject) {
        if (this == AObject) return true;
        if (AObject == null || getClass() != AObject.getClass()) return false;
        Tuple<?> tuple = (Tuple<?>) AObject;
        return Objects.equals(key, tuple.key) && Objects.equals(value, tuple.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
