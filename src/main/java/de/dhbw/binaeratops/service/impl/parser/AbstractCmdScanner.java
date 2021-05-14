package de.dhbw.binaeratops.service.impl.parser;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxMissingException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxUnexpectedException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Basisklasse für simple zustandsbehaftete Scanner.
 *
 * @author Nicolas Haug
 */
@Scope(value = "session")
@Service
public abstract class AbstractCmdScanner {

    private String input;

    /**
     * Aktuelles Token.
     * <p>
     * {@code null}, wenn bereits alles gaparst wurde.
     */
    private String currentToken = null;

    /**
     * Index auf den Anfang des aktuellen Tokens in der Eingabe.
     * <p>
     * Wurde noch kein Token ermittelt, dann ist der Index kleiner 0.
     * <p>
     * Wurde die gesamte Eingabe abgearbeitet, dann ist der Index
     * gleich der Länge der Eingabe.
     */
    private int currentTokenPos = -1;

    /**
     * Das Scannen beginnt an diesem Offset.
     *
     * @see #startWithPos(int)
     */
    private int startPos = 0;

    /**
     * Eingabe scannen und entsprechende Callbacks aufrufen.
     * <p>
     * Kann für eine Scanner-Instanz mehrfach aufgerufen werden,
     * der Zustand des Scanners wird jeweils zurückgesetzt.
     *
     * @param AInput   Zu parsende Eingabe.
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar  Avatar, der den Befehl ausführt.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException            Fehlermeldung oder Signalisierung von Zustandswechseln.
     * @throws InvalidImplementationException Übergebenes Objekt ungültig.
     */
    public UserMessage scan(String AInput, DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        input = AInput;
        currentToken = null;
        currentTokenPos = -1;

        return scanStart(ADungeon, AAvatar, AUser);
    }

    /**
     * Callback bei einem Syntaxfehler (unbekanntes Schlüsselwort).
     */
    protected void onUnexpectedToken() throws CmdScannerException {
        throw new CmdScannerSyntaxUnexpectedException(currentToken, currentTokenPos);
    }

    /**
     * Callback bei einem Syntaxfehler (unerwartetes Befehlsende, fehlendes Schlüsselwort).
     *
     * @param AExpected Erwartetes Schlüsselwort.
     * @throws CmdScannerException Fehlermeldung oder Signalisierung von Zustandswechseln.
     */
    protected void onMissingToken(String AExpected) throws CmdScannerException {
        throw new CmdScannerSyntaxMissingException(AExpected);
    }

    /**
     * Scannen im Zustand "Start".
     * <p>
     * Muss in einer konkreten Klasse überschrieben werden.
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar  Avatar, der den Befehl ausführt.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @throws CmdScannerException            Fehlermeldung oder Signalisierung von Zustandswechseln.
     * @throws InvalidImplementationException Übergebenes Objekt ungültig.
     */
    protected abstract UserMessage scanStart(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Hiermit kann der Index in der Eingabe festgelegt werden, an dem
     * das Scanning beginnen soll.
     * <p>
     * Hiermit können z. B. Präfixe übersprungen werden. Alterantiv könnten
     * natürlich solche Präfixe auch vor Übergabe an den Scanner entfernt
     * werden, dies würde jedoch Positionsangaben in Fehlermeldungen verfälschen.
     * <p>
     * Default ist 0.
     *
     * @param APos Position. Muss größer 0 sein.
     */
    protected void startWithPos(int APos) {
        if (currentTokenPos >= 0) {
            throw new IllegalStateException(
                    "Programmierfehler - startWithPos() darf nur VOR Beginn des Scan-Vorgangs aufgerufen werden!");
        }
        startPos = APos;
    }

    /**
     * Liefert aus dem restlichen zu parsenden String das erste Wort.
     *
     * @return 1. Wort. {@code null}, wenn bereits alles geparst wurde.
     */
    protected String findNextToken() {
        return findInput(false);
    }

    /**
     * Liefert die gesamte restliche Eingabe hinter dem aktuellen Token.
     *
     * @return Restliche Eingabe. {@code null}, wenn hinter dem aktuellen
     * Token keine Eingabe vorhanden ist.
     */
    protected String findRestOfInput() {
        return findInput(true);
    }

    /**
     * Liefert aus dem restlichen zu parsenden String das erste Wort.
     *
     * @param AUpToLineEnd Wahrheitswert, ob bis zum Stringende eingelesen werden soll.
     * @return 1. Wort. {@code null}, wenn bereits alles geparst wurde.
     */
    private String findInput(boolean AUpToLineEnd) {
        // Nur suchen, wenn wir noch nicht am Ende sind
        if (currentTokenPos < input.length()) {
            int n;
            if (currentTokenPos < 0) {
                // Wenn noch kein Token gesucht wurde, dann starten wir mit
                // der Suche des nächsten Tokens am String-Anfang ...
                n = startPos;
            } else {
                // ... ansonsten hinter dem aktuellen Token
                n = currentTokenPos + currentToken.length();
            }

            // Anfang nächstes Token finden
            for (; n < input.length() && input.charAt(n) == ' '; n++) {
            }

            if (n >= input.length()) {
                // Kein Token gefunden
                currentToken = null;
                currentTokenPos = n;

            } else {
                // Tokenanfang gefunden, Ende suchen
                currentTokenPos = n;
                if (AUpToLineEnd) {
                    n = input.length();
                } else {
                    for (; n < input.length() && input.charAt(n) != ' '; n++) {
                    }
                }
                currentToken = input.substring(currentTokenPos, n);
            }
        }
        return currentToken;
    }
}
