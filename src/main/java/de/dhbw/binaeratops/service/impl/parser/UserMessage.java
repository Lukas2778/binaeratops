package de.dhbw.binaeratops.service.impl.parser;

import de.dhbw.binaeratops.service.api.parser.UserMessageI;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse für eine Benutzernachricht.
 * <p>
 * Stellt alle Funktionalitäten zum Umgang mit einer Benutzernachricht bereit.
 * </p>
 * <p>
 * Für Schnittstelle siehe @{@link UserMessageI}.
 * </p>
 *
 * @author Nicolas Haug
 */
public class UserMessage implements UserMessageI {
    private final List<String> params = new ArrayList<>();
    private final String key;

    /**
     * Konstruktor zum Erzeugen einer Benutzernachricht.
     *
     * @param AKey    Schlüssel der Nachricht.
     * @param AObject Parameter der Nachricht.
     */
    public UserMessage(String AKey, String... AObject) {
        key = AKey;
        for (String s : AObject) {
            params.add(s);
        }
    }

    public List<String> getParams() {
        return params;
    }

    public String getKey() {
        return key;
    }
}