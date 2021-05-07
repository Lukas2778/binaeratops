package de.dhbw.binaeratops.service.impl.chat;

import com.vaadin.flow.component.html.Paragraph;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import de.dhbw.binaeratops.service.api.chat.PlayerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@Deprecated
@Service
public class PlayerService
        implements PlayerServiceI
{







    DungeonRepositoryI dungeonRepo;
    @Autowired
    UserRepositoryI userRepo;

    @Override
    public void updateMessageList( Flux<ChatMessage> messages)
    {
//        messages.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> messageList.add(new Paragraph(
//                message.getFrom() + ": " + message.getMessage())));
  }
}
