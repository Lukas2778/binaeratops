package de.dhbw.binaeratops;

import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.streammessages.RequestAnswer;
import de.dhbw.binaeratops.model.streammessages.UserAction;
import de.dhbw.binaeratops.model.streammessages.UserRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.Locale;

/**
 * The entry point of the Spring Boot application.
 */

@SpringBootApplication
@Configuration
public class Application extends SpringBootServletInitializer {




    @Bean
    UnicastProcessor<ChatMessage> publisher(){
        return UnicastProcessor.create();
    }

    @Bean
    Flux<ChatMessage> messages(UnicastProcessor<ChatMessage> publisher) {
        return publisher.replay(30).autoConnect();
    }

    @Bean
    UnicastProcessor<UserRequest> permissionPublisher(){
        return UnicastProcessor.create();
    }

    @Bean
    Flux<UserRequest> permissions(UnicastProcessor<UserRequest> APermissionPublisher) {
        return APermissionPublisher.replay(30).autoConnect();
    }

    @Bean
    UnicastProcessor<RequestAnswer> requestAnswerPublisher(){
        return UnicastProcessor.create();
    }

    @Bean
    Flux<RequestAnswer> requestAnswers(UnicastProcessor<RequestAnswer> ARequestAnswerPublisher) {
        return ARequestAnswerPublisher.replay(30).autoConnect();
    }


    @Bean
    UnicastProcessor<UserAction> userActionsPublisher(){
        return UnicastProcessor.create();
    }

    @Bean
    Flux<UserAction> userActions(UnicastProcessor<UserAction> AUserActionsPublisher) {
        return AUserActionsPublisher.replay(30).autoConnect();
    }

//    @Bean
//    UnicastProcessor<UserAction> userActionsPublisher(){
//        return UnicastProcessor.create();
//    }
//
//    @Bean
//    Flux<UserAction> userActionsPublisher(UnicastProcessor<UserAction> AUserActionsPublisher) {
//        return AUserActionsPublisher.replay(30).autoConnect();
//    }

    /**
     * Main-Methode der Applikation.
     * @param args Argumente zum Starten der Anwendung.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMANY);
        SpringApplication.run(Application.class, args);
    }

}
