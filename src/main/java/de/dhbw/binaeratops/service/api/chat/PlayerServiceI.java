package de.dhbw.binaeratops.service.api.chat;

import de.dhbw.binaeratops.model.chat.ChatMessage;
import reactor.core.publisher.Flux;

public interface PlayerServiceI
{

    /**
     * MessageList aktualisieren.
     * @param messages Subscriber der die Nachricht des Publisher liest und weiterschickt
     */
    public void updateMessageList(Flux<ChatMessage> messages);



}
