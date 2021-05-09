package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.impl.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Oberfläche des Tabs 'Lobby'
 */
@Route(value = "lobby")
@PageTitle("Lobby")
public class LobbyView extends VerticalLayout {
    H1 titleText;
    String explanationText;
    Html html;

    private List<Dungeon> dungeonList;
    Grid<Dungeon> dungeonGrid;

    DungeonServiceI dungeonServiceI;

    GameService gameService;


    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Lobby'.
     */
    public LobbyView(@Autowired DungeonServiceI ADungeonService, @Autowired GameService gameService){
        dungeonServiceI=ADungeonService;
        this.gameService = gameService;

        titleText=new H1("Dungeon spielen");
        explanationText=new String("<div>Du kannst hier aus einer Lister der für dich spielbaren Dungeons auswählen.<br>" +
                "Es werden dir hier alle öffentlich zugänglichen Dungeons angezeigt, sowie alle Dungeons für die du " +
                "die Berechtigung hast, sie zu spielen (privat).<br>Viel Spaß ;)</div>");
        html=new Html(explanationText);
        dungeonList = new ArrayList<>();

        User user = VaadinSession.getCurrent().getAttribute(User.class);
        dungeonList.addAll(dungeonServiceI.getAllDungeonsFromUser(user));

        dungeonGrid=new Grid<>();
        dungeonGrid.setItems(dungeonList);
        dungeonGrid.setVerticalScrollingEnabled(true);
        dungeonGrid.addColumn(Dungeon::getDungeonName).setHeader("Name");
        dungeonGrid.addColumn(Dungeon::getDungeonId).setHeader("Dungeon ID");
        dungeonGrid.addColumn(Dungeon::getDescription).setHeader("Beschreibung");
        dungeonGrid.addColumn(Dungeon::getDungeonVisibility).setHeader("Sichtbarkeit");
        dungeonGrid.addColumn(Dungeon::getDungeonStatus).setHeader("Status");
        dungeonGrid.addComponentColumn(item -> createEntryButton(dungeonGrid, item)).setHeader("Aktion");

        add(titleText, html, dungeonGrid);

        setSizeFull ();
        //setJustifyContentMode ( FlexComponent.JustifyContentMode.CENTER ); // Put content in the middle horizontally.
        //setDefaultVerticalComponentAlignment ( FlexComponent.Alignment.CENTER ); // Put content in the middle vertically.
    }

    private Button createEntryButton(Grid<Dungeon> AGrid, Dungeon ADungeon) {

        Button button = new Button("", clickEvent -> {
            ListDataProvider<Dungeon> dataProvider = (ListDataProvider<Dungeon>) AGrid
                    .getDataProvider();
            gameService.updateView(ADungeon.getDungeonId());
            //TODO
        });

        Icon iconEntryButton = new Icon(VaadinIcon.ENTER);
        button.setIcon(iconEntryButton);

        button.getStyle().set("color", "blue");
        return button;
    }
}
