package de.dhbw.binaeratops.service.impl.chat;

import com.sun.xml.bind.v2.TODO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import de.dhbw.binaeratops.service.api.chat.PlayerServiceI;
import de.dhbw.binaeratops.service.events.ChatEvent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.UnicastProcessor;


@Service
@Tag("Service")
/**
 * Service zum versenden von nachrichten.
 */
public class PlayerService extends Component
        implements PlayerServiceI {

    private final UnicastProcessor publisher;

    public PlayerService(UnicastProcessor publisher) {
        this.publisher = publisher;
    }

    @Override
    public void sendInput(String AMessage, User AUser) {
        //Parser aufrufen
    }
}
