package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.impl.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.UnicastProcessor;

import java.util.*;

/**
 * Tab-Oberfläche für die Komponente "Lobby" des Hauptmenüs.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für die Lobby aller aktiven und öffentlichen Dungeons bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Lukas Göpel, Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall
 */
public class LobbyView extends VerticalLayout implements HasDynamicTitle {

    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    private Timer timer;

    H1 titleText;
    String explanationText;
    Html html;

    private List<Dungeon> dungeonList;
    Grid<Dungeon> dungeonGrid;

    DungeonServiceI dungeonServiceI;

    GameService gameService;
    User currentUser;

    private final UnicastProcessor<UserAction> userActionpublisher;

    private HashMap<Dungeon, Button> entryButtonMap = new HashMap<>();


    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Lobby'.
     * @param ADungeonService DungeonService.
     * @param AGameService GameService.
     * @param AUserActionPublisher UserActionPublisher.
     */
    public LobbyView(@Autowired DungeonServiceI ADungeonService, @Autowired GameService AGameService, UnicastProcessor<UserAction> AUserActionPublisher){
        this.userActionpublisher = AUserActionPublisher;
        dungeonServiceI=ADungeonService;
        this.gameService = AGameService;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshView();
            }
        }, 0, 2000);

        this.currentUser = VaadinSession.getCurrent().getAttribute(User.class);

        titleText=new H1(res.getString("view.lobby.headline"));
        explanationText=res.getString("view.lobby.text");
        html=new Html(explanationText);
        dungeonList = new ArrayList<>();

        User user = VaadinSession.getCurrent().getAttribute(User.class);
        dungeonList.addAll(dungeonServiceI.getDungeonsLobby(user));

        dungeonGrid=new Grid<>();
        dungeonGrid.setItems(dungeonList);
        dungeonGrid.setVerticalScrollingEnabled(true);
        dungeonGrid.addColumn(Dungeon::getDungeonName).setHeader(res.getString("view.lobby.grid.dungeonname"));
        dungeonGrid.addColumn(Dungeon::getDungeonId).setHeader(res.getString("view.lobby.grid.dungeonid"));
        dungeonGrid.addColumn(Dungeon::getDescription).setHeader(res.getString("view.lobby.grid.description"));
        dungeonGrid.addColumn(Dungeon::getDungeonVisibility).setHeader(res.getString("view.lobby.grid.visibility"));
        dungeonGrid.addColumn(Dungeon::getDungeonStatus).setHeader(res.getString("view.lobby.grid.status"));
        dungeonGrid.addComponentColumn(item -> createEntryButton(dungeonGrid, item)).setHeader(res.getString("view.lobby.grid.action"));

        add(titleText, html, dungeonGrid);

        setSizeFull ();
    }
    // TODO Kommentare schreiben
    private Button createEntryButton(Grid<Dungeon> AGrid, Dungeon ADungeon) {

        Button entryButton = new Button("", clickEvent -> {
            currentUser = VaadinSession.getCurrent().getAttribute(User.class);
            Permission permissionGranted = dungeonServiceI.getPermissionGranted(currentUser, ADungeon);
            Permission permissionBlocked = dungeonServiceI.getPermissionBlocked(currentUser, ADungeon);
            Permission permission = dungeonServiceI.getPermissionRequest(currentUser, ADungeon);
            if (permissionGranted == null && permissionBlocked == null) { // TODO User
                if (permission == null) {
                    Permission requested = new Permission(currentUser);
                    ADungeon.addRequestedUser(requested);
                    dungeonServiceI.savePermission(requested);
                    UserAction userAction = new UserAction(ADungeon, currentUser, requested, ActionType.ENTRY_REQUEST);
                    dungeonServiceI.saveUserAction(userAction);
                    userActionpublisher.onNext(userAction);
                    Notification.show(res.getString("view.lobby.notification.request.sent"));
                } else {
                    Notification.show(res.getString("view.lobby.notification.request.idle"));
                }
            } else if (permissionBlocked != null) {
                Notification.show(res.getString("view.lobby.notification.request.denied"));
            } else {
                UI.getCurrent().navigate("game/" + ADungeon.getDungeonId());
            }
        });

        entryButtonMap.put(ADungeon, entryButton);

        Icon iconEntryButton = new Icon(VaadinIcon.ENTER);
        entryButton.setIcon(iconEntryButton);

        entryButton.getStyle().set("color", "blue");
        return entryButton;
    }

    private void refreshView() {
        getUI().ifPresent(ui -> ui.access(() -> {
            reloadGrid();
        }));
    }

    private void reloadGrid() {
        dungeonList = new ArrayList<>();
        dungeonList.addAll(dungeonServiceI.getDungeonsLobby(currentUser));
        dungeonGrid.setItems(dungeonList);
        for (Map.Entry<Dungeon,Button> entry : entryButtonMap.entrySet()) {
            Dungeon dungeon = entry.getKey();
            Button b = entry.getValue();
//            b.getIcon().getElement().getStyle().clear();
            Permission permissionGranted = dungeonServiceI.getPermissionGranted(currentUser, dungeon);
            Permission permissionBlocked = dungeonServiceI.getPermissionBlocked(currentUser, dungeon);
            Permission permissionRequest = dungeonServiceI.getPermissionRequest(currentUser, dungeon);
            if (permissionGranted != null) {
//                b.removeThemeVariants(ButtonVariant.LUMO_CONTRAST);
//                b.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                b.getIcon().getElement().getStyle().set("color", "green");
                b.getElement().getStyle().set("color", "green");
                entry.setValue(b);
//                b.getStyle().set("background", "green");
            } else if (permissionBlocked != null) {
//                b.removeThemeVariants(ButtonVariant.LUMO_CONTRAST);
//                b.addThemeVariants(ButtonVariant.LUMO_ERROR);
//                b.getStyle().set("background", "red");
                b.getIcon().getElement().getStyle().set("color", "red");
            } else if (permissionRequest != null) {
//                b.getStyle().set("background", "orange");
                b.getIcon().getElement().getStyle().set("color", "orange");
            } else {
                b.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
//                b.getStyle().clear();
                b.getIcon().getElement().getStyle().set("color", "blue");
            }
        }
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.main.tab.lobby");
    }
}
