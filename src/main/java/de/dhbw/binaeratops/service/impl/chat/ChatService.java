package de.dhbw.binaeratops.service.impl.chat;

import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.UnicastProcessor;

@Service
/**
 * Service zum versenden von nachrichten.
 */
public class ChatService implements ChatServiceI
{
    private final UnicastProcessor publisher;

    public ChatService(UnicastProcessor publisher) {
        this.publisher = publisher;
    }

    public void sendMessage(String AMessage, User AReceiver){
        List<Long> receiverList = new ArrayList<>();
        receiverList.add(AReceiver.getUserId());
        publisher.onNext(new ChatMessage(AMessage, receiverList));
    }

    public void sendMessage(String AMessage, List<User> AReceiverList){
        List<Long> receiverList = new ArrayList<>();
        AReceiverList.forEach(user -> receiverList.add(user.getUserId()));
        publisher.onNext(new ChatMessage(AMessage, receiverList));
    }
}
