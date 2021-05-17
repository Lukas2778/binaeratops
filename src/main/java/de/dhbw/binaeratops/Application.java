package de.dhbw.binaeratops;

import de.dhbw.binaeratops.model.actions.KickUserAction;
import de.dhbw.binaeratops.model.actions.UserAction;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.Locale;

/**
 * Klasse zum Starten der Applikation.
 * <p>
 * Initialisiert den Chat und startet den Applikationsserver mithilfe von SpringBoot.
 * </p>
 *
 * @author Lukas Göpel, Nicolas Haug, Timon Gartung, Pedro Treuer, Matthias Rall, Lars Rösel
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    /**
     * Initialisiert die Bean für den Publisher die Chatkommunikation.
     *
     * @return Bean.
     */
    @Bean
    UnicastProcessor<ChatMessage> publisher() {
        return UnicastProcessor.create();
    }

    /**
     * Initialisiert die Bean für eine Benutzeraktion.
     *
     * @param APublisher Publisher für eine Benutzeraktion.
     * @return Bean.
     */
    @Bean
    Flux<ChatMessage> messages(UnicastProcessor<ChatMessage> APublisher) {
        return APublisher.replay(30).autoConnect();
    }

    /**
     * Initialisiert die Bean für den Publisher der Benutzeraktion.
     *
     * @return Bean.
     */
    @Bean
    UnicastProcessor<UserAction> userActionPublisher() {
        return UnicastProcessor.create();
    }

    /**
     * Initialisiert die Bean für eine Benutzeraktion.
     *
     * @param APublisher Publisher für eine Benutzeraktion.
     * @return Bean.
     */
    @Bean
    Flux<UserAction> userAction(UnicastProcessor<UserAction> APublisher) {
        return APublisher.replay(30).autoConnect();
    }

    /**
     * Initialisiert die Bean für den Publisher zum Benutzer bannen.
     *
     * @return Bean.
     */
    @Bean
    UnicastProcessor<KickUserAction> kickUserPublisher() {
        return UnicastProcessor.create();
    }

    /**
     * Initialisiert die Bean für den Subscriber zum Benutzer bannen.
     *
     * @param APublisher Publisher zum Benutzer bannen.
     * @return Bean.
     */
    @Bean
    Flux<KickUserAction> kickUsers(UnicastProcessor<KickUserAction> APublisher) {
        return APublisher.replay(30).autoConnect();
    }

    /**
     * Main-Methode der Applikation.
     *
     * @param args Argumente zum Starten der Anwendung.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMANY);
        SpringApplication.run(Application.class, args);
    }

}
