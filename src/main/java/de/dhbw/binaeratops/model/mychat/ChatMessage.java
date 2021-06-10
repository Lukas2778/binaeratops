package de.dhbw.binaeratops.model.mychat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Lukas Göpel
 * Date: 10.06.2021
 * Time: 10:00
 */

@Getter
@Setter
public class ChatMessage {

    private String from;
    private String text;
    private String recipient;
    private String time;

    public ChatMessage() {

    }

    public ChatMessage(String from, String text, String recipient) {
        this.from = from;
        this.text = text;
        this.recipient = recipient;
        this.time = StringUtils.getCurrentTimeStamp();
    }

}
