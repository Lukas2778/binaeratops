package de.dhbw.binaeratops.service.api.parser;

import java.util.List;

/**
 * Schnittstelle für eine Benutzernachricht.
 * <p>
 * Stellt alle Funktionalitäten zum Umgang mit einer Benutzernachricht bereit.
 * </p>
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.service.impl.parser.UserMessage}.
 * </p>
 *
 * @author Nicolas Haug
 */
public interface UserMessageI {
    /**
     * Gibt die Parameter der Benutzernachricht zurück.
     *
     * @return Parameter der Benutzernachricht.
     */
    List<String> getParams();

    /**
     * Gibt den Resource-Bundle Schlüssel zurück.
     *
     * @return Resource-Bundle Schlüssel.
     */
    String getKey();
}
