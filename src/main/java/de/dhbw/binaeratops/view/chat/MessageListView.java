package de.dhbw.binaeratops.view.chat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

/**
 * Vaadin-Komponente für die Komponente "Chat".
 * <p>
 * Diese Ansicht stellt die Chat-Ansicht dar.
 * <p>
 * Damit können hier alle Chat-Nachrichten in einer eigenen View dargestellt werden.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug
 */
public class MessageListView extends Div {

    /**
     * Standard-Konstruktor zum Erzeugen einer MessageList.
     */
    public MessageListView() {
        addClassName("message-list");
    }

    @Override
    public void add(Component... components) {
        super.add(components);
        components[components.length - 1]
                .getElement()
                .callFunction("scrollIntoView");

    }
}