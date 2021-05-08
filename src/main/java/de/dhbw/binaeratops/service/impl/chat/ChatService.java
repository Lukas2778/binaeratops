package de.dhbw.binaeratops.service.impl.chat;

import com.vaadin.flow.component.html.Paragraph;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.events.ChatEvent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;


@Service

public class ChatService implements ChatServiceI
{



    @Autowired
    private ApplicationEventPublisher eventPublisher;

//    @Bean
//    Flux<ChatMessage> messages(UnicastProcessor<ChatMessage> publisher)
//    {
//
//        return publisher.replay(30)
//                .autoConnect();
//    }


    DungeonRepositoryI dungeonRepo;
    @Autowired
    UserRepositoryI userRepo;

    @Override
    public void whisperAvatar(String AMessage, long AUserId)
    {
        List<User> oneUserList = new ArrayList<User>();
        oneUserList.add(userRepo.findByUserId(AUserId));

        String username = userRepo.findByUserId(AUserId).getName();

//        publisher().onNext(new ChatMessage(username, AMessage));
        eventPublisher.publishEvent(new ChatEvent(AMessage, oneUserList));


    }

    @Override
    public void speakToAllAvatars(String AMessage)
    {

    }

    @Override
    public void speak(String AMessage, long AUserId)
    {

    }
}
