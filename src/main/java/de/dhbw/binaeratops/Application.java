package de.dhbw.binaeratops;

import de.dhbw.binaeratops.model.KickUser;
import de.dhbw.binaeratops.model.UserAction;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.Locale;

/**
 * The entry point of the Spring Boot application.
 */

@SpringBootApplication
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
    UnicastProcessor<UserAction> userActionPublisher(){
        return UnicastProcessor.create();
    }

    @Bean
    Flux<UserAction> userAction(UnicastProcessor<UserAction> publisher) {
        return publisher.replay(30).autoConnect();
    }

    /**
     * Main-Methode der Applikation.
     * @param args Argumente zum Starten der Anwendung.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMANY);
        SpringApplication.run(Application.class, args);
    }

}
