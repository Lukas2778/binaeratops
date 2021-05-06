package de.dhbw.binaeratops.service.impl.parser.gamectrl;

import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.service.api.parser.GameCtrlCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.AbstractCmdScanner;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Simpler zustandsbehafteter Scanner zur Interpretation der "In-Game"-Befehle.
 *
 * @author Nicolas Haug
 */
@Scope(value = "session")
@Service
public class GameCtrlCmdScanner extends AbstractCmdScanner {

    private final GameCtrlCmdHooksI hooks;

    /**
     * Konstruktor.
     *
     * @param AHooks Callbacks für die gefundenen Befehle.
     */
    public GameCtrlCmdScanner(GameCtrlCmdHooksI AHooks) {
        hooks = AHooks;
    }

    /**
     * Scanner im Zustand "Start".
     */
    protected UserMessage scanStart() throws CmdScannerException {
        String token = findNextToken();
        if (token == null) {
            onMissingToken("Befehl");
        } else {
            switch (token.toLowerCase()) {
                case "move" :
                    _scanMove1();
                    break;
                case "punch" :
                    _scanPunch1();
                    break;
                default :
                    // Sonderfall des "move"-Befehls, wenn auf die Angabe des Schlüsselwirt "move"
                    // verzichtet wird?
                    Direction direction = convertToDirection(token);
                    if (direction != null) {
                        _scanMove2(direction);
                    } else {
                        onUnexpectedToken();
                    }
                    break;
            }
        }
        return new UserMessage("f");
    }

    /**
     * Scanner Zustand "move" Level 1 - Ereknnen Richtung.
     */
    private void _scanMove1() throws CmdScannerException {
        String token = findNextToken();
        if (token == null) {
            onMissingToken("<direction>");
        } else {
            Direction direction = convertToDirection(token);
            if (direction == null) {
                onUnexpectedToken();
            } else {
                _scanMove2(direction);
            }
        }
    }
    /**
     * Scanner Zustand "move" Level 2 - Ermitteln Schritte.
     */
    private void _scanMove2(Direction ADirection) throws CmdScannerException {
        String token = findNextToken();
        if (token == null) {
            // Keine Schrittangabe -> 1 verwenden
            hooks.onCmdMove(ADirection, 1);
        } else {
            try {
                int steps = Integer.valueOf(token);
                if (steps <= 0) {
                    onUnexpectedToken();
                } else {
                    hooks.onCmdMove(ADirection, steps);
                }
            } catch(NumberFormatException e) {
                onUnexpectedToken();
            }
        }
    }

    /**
     * Scanner Zustand "punch" Level 1.
     */
    private void _scanPunch1() throws CmdScannerException {
        hooks.onCmdPunch();
    }

    /**
     * TODO
     * @param AToken
     * @return
     */
    private Direction convertToDirection(String AToken) {
        String token = AToken.toUpperCase();
        Direction direction = null;
        for (Direction d : Direction.values()) {
            if (d.toString().equals(token)) {
                direction = d;
                break;
            }
        }
        return direction;
    }
}
