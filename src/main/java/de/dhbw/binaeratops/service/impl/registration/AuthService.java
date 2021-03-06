package de.dhbw.binaeratops.service.impl.registration;

import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.exceptions.AuthException;
import de.dhbw.binaeratops.service.exceptions.FalseUserException;
import de.dhbw.binaeratops.service.exceptions.NotVerifiedException;
import de.dhbw.binaeratops.service.exceptions.RegistrationException;
import de.dhbw.binaeratops.view.MainView;
import de.dhbw.binaeratops.view.configurator.ConfiguratorMainView;
import de.dhbw.binaeratops.view.dungeonmaster.DungeonMasterView;
import de.dhbw.binaeratops.view.game.GameView;
import de.dhbw.binaeratops.view.mainviewtabs.AboutUsView;
import de.dhbw.binaeratops.view.mainviewtabs.LobbyView;
import de.dhbw.binaeratops.view.mainviewtabs.MyDungeonsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Komponente "AuthService".
 * <p>
 * Dieser Service stellt die Schnittstelle zwischen der User-Authentifizierung und der Benutzeroberfläche dar.
 * </p>
 * <p>
 * Für Schnittstelle dieser Komponente siehe {@link AuthServiceI}.
 * </p>
 *
 * @author Lukas Göpel, Matthias Rall
 */
@Service
public class AuthService implements AuthServiceI {

    @Autowired
    UserRepositoryI userRepository;

    @Autowired
    private MailService mailService;

    @Override
    public void authenticate(String AName, String APassword) throws AuthException, NotVerifiedException {
        User user;
        try {
            //Nutzer in der Datenbank finden
            user = userRepository.findByName(AName);
            if (user.checkPassword(APassword)) {
                if (!user.isVerified()) {
                    //Benutzer ist nicht verifiziert
                    throw new NotVerifiedException();
                } else {
                    VaadinSession.getCurrent().setAttribute(User.class, user);
                    createRoutes();
                }
            }
            else {
                throw new AuthException();//Nur den Benutzernamen eingegeben
            }
        } catch (Exception e) {
            throw new AuthException();
        }
    }

    @Override
    public void register(String AName, String APassword, String AEMail) throws RegistrationException {
        int code = new Random().nextInt(999999);

        if (userRepository.findByName(AName) != null) {
            throw new RegistrationException();
        }

        User user = new User(AName, AEMail, APassword, code, false);
        userRepository.save(user);
        mailService.sendVerificationMail(user, code);
    }

    @Override
    public boolean confirm(String AUserName, int ACode) {
        User user = userRepository.findByName(AUserName);
        if (user.getCode() == ACode && !user.isVerified()) {
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void sendConfirmationEmail(String AUserName) throws FalseUserException {
        User user = userRepository.findByName(AUserName);
        if (user != null) {
            int code = new Random().nextInt(999999);
            mailService.sendPasswordMail(user, code);
            user.setCode(code);
            //user.setVerified(false);
            userRepository.save(user);
        } else {
            throw new FalseUserException();
        }
    }

    @Override
    public void changePassword(String AUserName, String ANewPassword, int ACode) throws FalseUserException {
        User user = userRepository.findByName(AUserName);
        if (user != null) {
            if (ACode == user.getCode()) {
                user.setVerified(true);
                user.setPassword(ANewPassword);
                userRepository.save(user);
            }
        } else {
            throw new FalseUserException();
        }
    }

    @Override
    public void changePassword(long AUserId, String ANewPassword, String AOldPassword) {

    }

    /**
     * Seiten Verlinkung vornehmen ohne Menü.
     */
    private void createRoutes() {
        getRoutsForMenu().stream()
                .forEach(r ->
                        RouteConfiguration.forSessionScope().setRoute(r.getRoute(), r.getView(), MainView.class));
        getRouts().stream()
                .forEach(r ->
                        RouteConfiguration.forSessionScope().setRoute(r.getRoute(), r.getView()));
    }

    /**
     * Auflisten der Routen, die mit Menüband angezeigt werden.
     *
     * @return Rückgabe Routen.
     */
    private List<AuthorizedRoute> getRoutsForMenu(){
        List<AuthorizedRoute> returnList= new ArrayList<>();
        returnList.add(new AuthorizedRoute("aboutUs","Über uns", AboutUsView.class));
        returnList.add(new AuthorizedRoute("lobby","Lobby", LobbyView.class));
        returnList.add(new AuthorizedRoute("myDungeons","Eigene Dungeons", MyDungeonsView.class));
        returnList.add(new AuthorizedRoute("configurator","Konfigurator", ConfiguratorMainView.class));
        return returnList;
    }


    /**
     * Liste der verfügbaren Routen zurückgeben.
     * @return Liste der Routen.
     */
    private List<AuthorizedRoute> getRouts() {
        List<AuthorizedRoute> returnList = new ArrayList<>();
        returnList.add(new AuthorizedRoute("play/dungeonmaster","dungeonmaster", DungeonMasterView.class));
        returnList.add(new AuthorizedRoute("game","Spielansicht", GameView.class));
        return returnList;
    }
}
