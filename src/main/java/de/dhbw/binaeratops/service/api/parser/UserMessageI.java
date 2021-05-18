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
    List<String> getParams();

    String getKey();
}
