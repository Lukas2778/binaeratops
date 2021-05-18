package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.impl.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    H1 titleText;
    String explanationText;
    Html html;

    private List<Dungeon> dungeonList;
    Grid<Dungeon> dungeonGrid;

    DungeonServiceI dungeonServiceI;

    GameService gameService;


    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Lobby'.
     * @param ADungeonService DungeonService.
     * @param AGameService GameService.
     */
    public LobbyView(@Autowired DungeonServiceI ADungeonService, @Autowired GameService AGameService){
        dungeonServiceI=ADungeonService;
        this.gameService = AGameService;

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

        Button button = new Button("", clickEvent -> {
//            if (!ADungeon.getCurrentUsers().contains(VaadinSession.getCurrent().getAttribute(User.class))) {
//                ADungeon.addCurrentUser(VaadinSession.getCurrent().getAttribute(User.class));
//                dungeonServiceI.saveDungeon(ADungeon);
//            }
            UI.getCurrent().navigate("game/" + ADungeon.getDungeonId());
        });

        Icon iconEntryButton = new Icon(VaadinIcon.ENTER);
        button.setIcon(iconEntryButton);

        button.getStyle().set("color", "blue");
        return button;
    }


    @Override
    public String getPageTitle() {
        return res.getString("view.main.tab.lobby");
    }
}
