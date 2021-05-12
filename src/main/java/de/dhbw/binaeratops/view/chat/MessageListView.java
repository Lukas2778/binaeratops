package de.dhbw.binaeratops.view.chat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

/**
 * TODO JAVADOC Kommentar schreiben.
 *
 * @author Pedro Treuer, Timon Gartung
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