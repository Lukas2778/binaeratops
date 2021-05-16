package de.dhbw.binaeratops.model.map;

/**
 * Klasse für eine Kachel.
 * <p>
 * Stellt alle Funktionalitäten für den Umgang mit einer Kachel der Karte bereit.
 * <p>
 * Sie besteht aus einer X-Koordinate, einer Y-Koordinate und dem Pfad der Kachel.
 *
 * @author Matthias Rall, Nicolas Haug
 */
public class Tile {
    int x;
    int y;
    String path;

    /**
     * Konstruktor zum Erzeugen einer Kachel der Karte mit allen Eigenschaften.
     *
     * @param AXCoordinate X-Koordinate der Kachel.
     * @param AYCoordinate Y-Koordinate der Kachel.
     * @param APath        Pfad zu der Kachel.
     */
    public Tile(int AXCoordinate, int AYCoordinate, String APath) {
        this.x = AXCoordinate;
        this.y = AYCoordinate;
        this.path = APath;
    }

    /**
     * Standard-Konstruktor zum Erzeugen einer Kacekl.
     */
    public Tile() {
    }

    /**
     * Gibt die X-Koordinate der Kachel zurück.
     *
     * @return X-Koordinate der Kachel.
     */
    public int getX() {
        return x;
    }

    /**
     * Setzt die X-Koordinate der Kachel.
     *
     * @param AXCoordinate X-Koordinate der Kachel.
     */
    public void setX(int AXCoordinate) {
        this.x = AXCoordinate;
    }

    /**
     * Gibt die Y-Koordinate der Kachel zurück.
     *
     * @return Y-Koordinate der Kachel.
     */
    public int getY() {
        return y;
    }

    /**
     * Setzt die Y-Koordinate der Kachel.
     *
     * @param AYCoordinate Y-Koordinate der Kachel.
     */
    public void setY(int AYCoordinate) {
        this.y = AYCoordinate;
    }

    /**
     * Gibt den Pfad der Kachel zurück.
     *
     * @return Pfad der Kachel.
     */
    public String getPath() {
        return path;
    }

    /**
     * Setzt den Pfad der Kachel.
     *
     * @param APath Pfad der Kachel.
     */
    public void setPath(String APath) {
        this.path = APath;
    }
}
