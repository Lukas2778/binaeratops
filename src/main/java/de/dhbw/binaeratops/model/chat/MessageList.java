/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *
 */

package de.dhbw.binaeratops.model.chat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import java.util.List;

public class MessageList
        extends Div
{
    public MessageList(){
        addClassName("message-list");
    }

    @Override
    public void add(Component... components){
        super.add(components);
        components[components.length-1]
                .getElement()
                .callFunction("scrollIntoView");

    }
}
