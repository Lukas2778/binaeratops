package de.dhbw.binaeratops;

import de.dhbw.binaeratops.model.streammessages.ChatMessage;
import de.dhbw.binaeratops.model.streammessages.Permission;
import de.dhbw.binaeratops.model.streammessages.RequestAnswer;
import de.dhbw.binaeratops.model.streammessages.UserRequest;
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
    UnicastProcessor<UserRequest> userRequestPublisher(){
        return UnicastProcessor.create();
    }

    @Bean
    Flux<UserRequest> userRequestPublisher(UnicastProcessor<UserRequest> AUserRequestPublisher) {
        return AUserRequestPublisher.replay(30).autoConnect();
    }

    @Bean
    UnicastProcessor<ChatMessage> publisher(){
        return UnicastProcessor.create();
    }

    @Bean
    Flux<ChatMessage> messages(UnicastProcessor<ChatMessage> publisher) {
        return publisher.replay(30).autoConnect();
    }

    @Bean
    UnicastProcessor<Permission> permissionPublisher(){
        return UnicastProcessor.create();
    }

    @Bean
    Flux<Permission> permissions(UnicastProcessor<Permission> permissionPublisher) {
        return permissionPublisher.replay(30).autoConnect();
    }

    @Bean
    UnicastProcessor<RequestAnswer> requestAnswerPublisher(){
        return UnicastProcessor.create();
    }

    @Bean
    Flux<RequestAnswer> requestAnswers(UnicastProcessor<RequestAnswer> requestAnswerPublisher) {
        return requestAnswerPublisher.replay(30).autoConnect();
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
