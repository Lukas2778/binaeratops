package de.dhbw.binaeratops.model.mychat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Lukas Göpel
 * Date: 10.06.2021
 * Time: 10:02
 */

public class StringUtils {
    private static final String TIME_FORMATTER= "HH:mm:ss";

    public static String getCurrentTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMATTER);
        return LocalDateTime.now().format(formatter);
    }
}
