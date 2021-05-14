package de.dhbw.binaeratops.service.impl.permission;

import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.model.streammessages.Permission;
import de.dhbw.binaeratops.model.streammessages.RequestAnswer;
import de.dhbw.binaeratops.model.streammessages.UserRequest;
import de.dhbw.binaeratops.service.api.permission.PermissionServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.UnicastProcessor;


/**
 * Komponente "PermissionService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Versenden von Chatnachrichten bereit.
 * </p>
 * <p>
 * Für Schnittstelle dieser Komponente siehe @{@link PermissionServiceI}.
 * </p>
 *
 * @author Timon Gartung
 */
@Service
public class PermissionService implements PermissionServiceI {

    @Autowired
    private DungeonRepositoryI dungeonRepo;

    private final UnicastProcessor<UserRequest> userRequestsPublisher;
    private final UnicastProcessor<RequestAnswer> requestAnswerPublisher;

    /**
     *Konstruktor für den Service.
     *
     * @param AUserRequestsPublisher Publisher für die Berechtigungsanfrage.
     * @param APermissionPublisher
     */
    public PermissionService(UnicastProcessor<UserRequest> AUserRequestsPublisher, UnicastProcessor<RequestAnswer> APermissionPublisher) {
        this.userRequestsPublisher = AUserRequestsPublisher;
        this.requestAnswerPublisher = APermissionPublisher;
    }

    @Override
    public void requestPermission(User AUser, Dungeon ADungeon) {
        userRequestsPublisher.onNext(new UserRequest(ADungeon, AUser));
    }

    @Override
    public void answerRequest(User AUser, Dungeon ADungeon, boolean APermission) {
        if (APermission){
            giveDungeonPermission(AUser, ADungeon);
        }
        else {
            denyDungeonPermission(AUser, ADungeon);
        }
        sendAnswer(AUser, ADungeon, APermission);
    }

    @Override
    public void givePermission(User AUser, Dungeon ADungeon) {
        giveDungeonPermission(AUser, ADungeon);
        sendAnswer(AUser, ADungeon, true);
    }

    /**
     * Fügt dem Benutzer in die erlaubte Benutzerliste hinzu.
     * @param AUser Benutzer.
     * @param ADungeon Dungeon.
     */
    private void giveDungeonPermission(User AUser, Dungeon ADungeon){
        if(ADungeon.getBlockedUsers().contains(AUser)){
            ADungeon.removeBlockedUser(AUser);
        }
        ADungeon.addAllowedUser(AUser);
        dungeonRepo.save(ADungeon);
    }

    /**
     * Fügt dem Benutzer in die blockierte Benutzerliste hinzu.
     * @param AUser Benutzer.
     * @param ADungeon Dungeon.
     */
    private void denyDungeonPermission(User AUser, Dungeon ADungeon){
        if(ADungeon.getAllowedUsers().contains(AUser)){
            ADungeon.removeAllowedUser(AUser);
        }
        ADungeon.addBlockedUser(AUser);
        dungeonRepo.save(ADungeon);
    }

    /**
     * Sendet dem Benutzer eine Berechtigungsantwort.
     * @param AUser Benutzer.
     * @param ADungeon Dungeon.
     * @param APermission Spielberechtigung.
     */
    private void sendAnswer(User AUser, Dungeon ADungeon, boolean APermission){
        requestAnswerPublisher.onNext(new RequestAnswer(ADungeon, AUser, APermission));
    }
}

