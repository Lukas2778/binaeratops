package de.dhbw.binaeratops.model.chat;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse für eine Chat-Nachricht.
 *
 * @author Pedro Treuer, Timon Gartung
 */
public class ChatMessage {
    private String textMessage;
    private Paragraph paragraph;
    private boolean isParagraphMessage;
    private List<Long> userIdList;

    /**
     * Konstruktor der Nachricht mit mehreren Empfängern.
     *
     * @param message     Textnachricht.
     * @param AUserIdList Liste von Empfänger der Nachricht.
     */
    public ChatMessage(String message, List<Long> AUserIdList) {
        this.textMessage = message;
        this.userIdList = AUserIdList;
        this.paragraph = new Paragraph(new Label(""));
        this.isParagraphMessage = false;
    }

    /**
     * Konstruktor der Nachricht mit einem Empfänger.
     *
     * @param message Nur die Textnachricht ohne Absender.
     * @param AUserId Empfänger der Nachricht.
     */
    public ChatMessage(String message, Long AUserId) {
        this.textMessage = message;
        List<Long> list = new ArrayList<Long>();
        list.add(AUserId);
        this.userIdList = list;
        this.paragraph = new Paragraph(new Label(""));
        this.isParagraphMessage = false;
    }

    /**
     * Konstruktor der Nachricht als Paragraph mit mehreren Empfängern.
     *
     * @param AParagraph  Paragraph mit Absender.
     * @param AMessage    Nur die Textnachricht ohne Absender.
     * @param AUserIdList Liste von Empfänger der Nachricht.
     */
    public ChatMessage(Paragraph AParagraph, String AMessage, List<Long> AUserIdList) {
        this.paragraph = AParagraph;
        this.userIdList = AUserIdList;
        this.isParagraphMessage = true;
    }

    /**
     * Konstruktor der Nachricht als Paragraph mit einem Empfänger.
     *
     * @param AParagraph Paragraph mit Absender.
     * @param AMessage   Nur die Textnachricht ohne Absender.
     * @param AUserId    Empfänger der Nachricht.
     */
    public ChatMessage(Paragraph AParagraph, String AMessage, Long AUserId) {
        this.paragraph = AParagraph;
        List<Long> list = new ArrayList<Long>();
        list.add(AUserId);
        this.userIdList = list;
        this.isParagraphMessage = true;
    }

    /**
     * Gibt die Textnachricht zurück.
     *
     * @return Textnachricht.
     */
    public String getText() {
        return textMessage;
    }

    /**
     * Gibt die Liste der Benutzer zurück, an welche die Chat-Nachricht geschickt werden soll.
     *
     * @return Liste der Benutzer, an welche die Chat-Nachricht geschickt werden soll.
     */
    public List<Long> getUserIdList() {
        return userIdList;
    }

    /**
     * Gibt die Nachricht mit farbigen Absender als Paragraph zurück.
     *
     * @return Nachricht mit farbigen Absender.
     */
    public Paragraph getParagraph() {
        return paragraph;
    }

    /**
     * Gibt "true" zurück falls ein Paragraph erstellt wurde.
     *
     * @return Boolean.
     */
    public boolean IsParagraph() {
        return isParagraphMessage;
    }
}
