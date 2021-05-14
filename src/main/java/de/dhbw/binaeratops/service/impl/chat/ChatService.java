package de.dhbw.binaeratops.service.impl.chat;

import de.dhbw.binaeratops.model.streammessages.ChatMessage;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import org.springframework.stereotype.Service;
import reactor.core.publisher.UnicastProcessor;

import java.util.ArrayList;
import java.util.List;


@Service
/**
 * Komponente "ChatService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Versenden von Chatnachrichten bereit.
 * </p>
 * <p>
 * Für Schnittstelle dieser Komponente siehe @{@link ChatServiceI}.
 * </p>
 *
 * @author Timon Gartung, Pedro Treuer
 */
public class ChatService implements ChatServiceI
{
    private final UnicastProcessor publisher;

    /**
     * Konstruktor zum Erzeugen eines ChatService.
     * @param publisher Publisher.
     */
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
