package de.dhbw.binaeratops.view.dungeonmaster;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.RequestHandler;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.impl.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Comparator;
import java.util.Enumeration;

/**
 * @author Mathias Rall
 * Date: 07.05.2021
 * Time: 16:20
 */

@CssImport("./views/game/map-master.css")
@PageTitle("Title")
@Push
public class DungeonMasterView extends Div implements HasUrlParameter<Long>, RouterLayout {
    private final int WIDTH = 8;
    Image[][] tiles;

    private final SplitLayout splitChatWithRest = new SplitLayout();
    private final SplitLayout splitMapAndRoomWithActions = new SplitLayout();
    private final SplitLayout splitMapWithRoom = new SplitLayout();

    private final Grid<Avatar> grid = new Grid<>(Avatar.class);

    private GameService gameService;

    private MapServiceI mapServiceI;

    private DungeonServiceI dungeonServiceI;
    private DungeonRepositoryI dungeonRepositoryI;
    Dungeon dungeon;

    public DungeonMasterView(@Autowired MapServiceI mapServiceI, @Autowired GameService gameService, @Autowired DungeonServiceI dungeonServiceI, @Autowired DungeonRepositoryI dungeonRepositoryI) {
        this.mapServiceI = mapServiceI;
        this.gameService = gameService;
        this.dungeonServiceI = dungeonServiceI;
        this.dungeonRepositoryI = dungeonRepositoryI;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        gameService.initialize(dungeon, attachEvent.getUI(), this);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long ALong) {
        dungeon = dungeonRepositoryI.findByDungeonId(ALong);
        createLayoutBasic(ALong);
    }

    void createLayoutBasic(Long ALong) {
        setSizeFull();

        splitChatWithRest.setSizeFull();
        splitChatWithRest.addToPrimary(new VerticalLayout()); //TODO chat ergänzen
        splitChatWithRest.addToSecondary(splitMapAndRoomWithActions);

        splitMapAndRoomWithActions.setSizeFull();
        splitMapAndRoomWithActions.setOrientation(SplitLayout.Orientation.VERTICAL);
        splitMapAndRoomWithActions.addToPrimary(splitMapWithRoom);
        createLayoutAction();

        splitMapWithRoom.setSizeFull();
        splitMapWithRoom.addToPrimary(initMap(ALong));
        splitMapWithRoom.addToSecondary(new Label("AKTUELLER RAUM"));

        add(splitChatWithRest);
    }

    private void createLayoutAction() {
        createGrid();

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        Button actionsButton = new Button("Aktionen");
        Button npcsButton = new Button("NPCs");
        Button authorisationButton = new Button("Spielberechtigungen");
        Button pauseButton = new Button("Dungeon pausieren");
        Button leaveButton = new Button("Dungeon verlassen");
        addClickListeners(actionsButton, npcsButton, authorisationButton, pauseButton, leaveButton);
        vl.add(actionsButton, npcsButton, authorisationButton, pauseButton, leaveButton);
        hl.add(grid, vl);
        splitMapAndRoomWithActions.addToSecondary(hl);
    }

    private void addClickListeners(Button actionsButton, Button npcsButton, Button authorisationButton, Button pauseButton, Button leaveButton) {
        actionsButton.addClickListener(e -> {
            Dialog actionDialog = new Dialog(new Text("Aktionen zur Auswahl"));
            actionDialog.open();
        });
        npcsButton.addClickListener(e -> {
            Dialog npcDialog = new Dialog(new Text("NPCs zur Auswahl"));
            npcDialog.open();
        });
        authorisationButton.addClickListener(e -> {
            Dialog authorisationDialog = new Dialog(new Text("Neue Anfragen und aktuelle Spieler"));
            authorisationDialog.open();
        });
        pauseButton.addClickListener(e -> {
            Notification.show("Not Implemented");
        });
        leaveButton.addClickListener(e -> {
            Dialog leaveDialog = new Dialog(new Text("Willst du den Dungeon wirklich verlassen? Willst du einen anderen DM auswählen?"));
            leaveDialog.open();
        });
    }

    private void createGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        Avatar testAvatar = new Avatar();
        testAvatar.setAvatarId(99999999L);
        testAvatar.setName("Awodur");
        grid.setItems(testAvatar);

        grid.addColumn(Avatar::getName)
                .setComparator((avater1, avatar2) -> avater1.getName()
                        .compareToIgnoreCase(avatar2.getName()))
                .setHeader("Name");
        grid.addColumn(Avatar::getRoomId)
                .setComparator(Comparator.comparing(Avatar::getRoomId))
                .setHeader("Raum");
        grid.addComponentColumn(avatar -> {
            Button requestsButton = new Button("Anfragen");
            Dialog requestDialog = new Dialog(new Label("Informationen zur Anfrage von: " + avatar.getName()));
            requestsButton.addClickListener(e -> {
                requestDialog.open();
            });
            return requestsButton;
        }).setHeader("Anfragen");
        grid.addComponentColumn(avatar -> {
            Button whisperButton = new Button("Whisper");
            whisperButton.addClickListener(e -> {
                Notification.show("Not Implemented");
            });
            return whisperButton;
        }).setHeader("Anflüstern");
        grid.addComponentColumn(avatar -> {
            Button infoButton = new Button("Infos");
            Dialog infoDialog = new Dialog(new Label("Informationen zum Avatar: " + avatar.getName()));
            infoButton.addClickListener(e -> {
                infoDialog.open();
            });
            return infoButton;
        }).setHeader("Informationen");
    }

    VerticalLayout initMap(Long ADungeonId) {
        Tile[][] newTiles = mapServiceI.getMapGame(ADungeonId);
        tiles = new Image[newTiles.length][newTiles[0].length];
        VerticalLayout columns = new VerticalLayout();
        columns.setSpacing(false);
        for (int i = 0; i < newTiles.length; i++) {
            HorizontalLayout rows = new HorizontalLayout();
            rows.setSpacing(false);
            for (int j = 0; j < newTiles[0].length; j++) {
                tiles[i][j] = new Image("map/" + newTiles[i][j].getPath() + ".png", "Room");
                tiles[i][j].addClassName("room");
                rows.add(tiles[i][j]);
            }
            columns.add(rows);
        }
        return columns;
    }
}
