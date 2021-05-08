package de.dhbw.binaeratops.service.impl.chat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import de.dhbw.binaeratops.service.api.chat.PlayerServiceI;
import de.dhbw.binaeratops.service.events.ChatEvent;
import de.dhbw.binaeratops.service.events.UpdateChatEvent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@Service
@Tag("Service")


public class PlayerService extends Component
        implements PlayerServiceI {


    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    ChatServiceI chatService;

    @Override
    public void sendInput(String AMessage, User AUser) {
        chatService.whisperAvatar(AMessage, AUser.getUserId());
    }

    @Override
    public void updateMessageList(String  AMessage, List<User> AUserList) {
        publisher.publishEvent(new UpdateChatEvent(AMessage));
    }

    @Async
    @EventListener(ChatEvent.class)
    void handleNewChatEvent(ChatEvent AEvent){
        updateMessageList(AEvent.getMessage(), AEvent.getUserList());
    }
}
