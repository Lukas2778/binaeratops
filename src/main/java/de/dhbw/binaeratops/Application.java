package de.dhbw.binaeratops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Locale;

/**
 * The entry point of the Spring Boot application.
 */

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    /**
     * Main-Methode der Applikation.
     * @param args Argumente zum Starten der Anwendung.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMANY);
        SpringApplication.run(Application.class, args);
    }

}
