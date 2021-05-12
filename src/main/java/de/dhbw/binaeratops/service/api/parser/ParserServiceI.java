package de.dhbw.binaeratops.service.api.parser;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Interface für die Komponente "ParserService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Parsen der Spielereingabe bereit.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe @{@link de.dhbw.binaeratops.service.impl.parser.ParserService}.
 * </p>
 *
 * @author Nicolas Haug, Lukas Göpel
 */
public interface ParserServiceI {

    /**
     * Zerlegt die Spielereingabe.
     *
     * @param AInput     Spielereingabe.
     * @param ADungeonId Dungeon, in dem der Befehl abgesetzt wird.
     * @param AAvatar    Avatar, der den Befehl absetzt.
     * @param AUser      Spieler, der den Befehl absetzt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Übergebenes Objekt ungültig.
     */
    UserMessage parseCommand(String AInput, Long ADungeonId, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Gibt das Befehlssymbol des Dungeons zurück.
     *
     * @param ADungeonId Dungeon, für den das Befehlssymbol zurückgegeben werden soll.
     * @return Befehlssymbol des Dungeons.
     */
    Character getCommandSymbol(Long ADungeonId);
}
