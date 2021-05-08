package de.dhbw.binaeratops.service.impl.chat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.service.events.UpdateChatEvent;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import de.dhbw.binaeratops.service.api.chat.PlayerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Deprecated
@Service
@Tag("Service")
@Controller
public class PlayerService extends Component
        implements PlayerServiceI {


    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    ChatServiceI chatService;

    @Override
    public void sendInput(String AMessage, User AUser) {
        chatService.whisperAvatar(AMessage, AUser.getUserId());
        publisher.publishEvent(new UpdateChatEvent(AMessage));
    }

    @Override
    public void updateMessageList(String  AMessage, List<User> AUserList) {
        publisher.publishEvent(new UpdateChatEvent(AMessage));
    }
}
