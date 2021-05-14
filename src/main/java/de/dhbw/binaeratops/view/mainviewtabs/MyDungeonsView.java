package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Oberfläche des Tabs 'Eigene Dungeons'
 */
@PageTitle("Eigene Dungeons")
public class MyDungeonsView extends VerticalLayout {

    private List<Dungeon> dungeonList;
    private Button newDungeonButton;
    private Button editDungeonButton;
    private HorizontalLayout buttonsLayout;
    private H1 title;

    DungeonServiceI dungeonServiceI;
    ConfiguratorServiceI configuratorServiceI;

    Grid<Dungeon> dungeonGrid;


    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Eigene Dungeons'.
     *
     * @param ADungeonService      DungeonService.
     * @param AConfiguratorService KonfiguratorService.
     */
    public MyDungeonsView(@Autowired DungeonServiceI ADungeonService, @Autowired ConfiguratorServiceI AConfiguratorService) {
        dungeonServiceI = ADungeonService;
        configuratorServiceI = AConfiguratorService;

        dungeonList = new ArrayList<>();
        newDungeonButton = new Button("Dungeon erstellen");
        editDungeonButton = new Button("Bearbeiten");
        buttonsLayout = new HorizontalLayout();
        title = new H1("Meine Dungeons");

        User user = VaadinSession.getCurrent().getAttribute(User.class);
        dungeonList.addAll(dungeonServiceI.getAllDungeonsFromUser(user));

        initButtonsLayout();
        initEditDungeonButton();
        initNewDungeonButton();
        dungeonGrid = new Grid<>();
        dungeonGrid.setItems(dungeonList);
        dungeonGrid.setVerticalScrollingEnabled(true);
        dungeonGrid.addColumn(Dungeon::getDungeonName).setHeader("Name");
        dungeonGrid.addColumn(Dungeon::getDescription).setHeader("Beschreibung");
        dungeonGrid.addColumn(Dungeon::getDungeonVisibility).setHeader("Sichtbarkeit");
        dungeonGrid.addColumn(Dungeon::getDungeonStatus).setHeader("Status");
        dungeonGrid.addComponentColumn(dungeon -> {
            Button button = new Button("Starten");
            button.addClickListener(e -> {
                UI.getCurrent().navigate("play/dungeonmaster/" + dungeon.getDungeonId().toString());
                dungeonServiceI.activateDungeon(dungeon.getDungeonId());
            });
            return button;
        }).setHeader("Spielstart");
        dungeonGrid.addComponentColumn(item -> createRemoveButton(dungeonGrid, item)).setHeader("Löschen");


        add(title, buttonsLayout, dungeonGrid);
        setSizeFull();
    }

    private Button createRemoveButton(Grid<Dungeon> AGrid, Dungeon ADungeon) {

        Button button = new Button("", clickEvent -> {
            ListDataProvider<Dungeon> dataProvider = (ListDataProvider<Dungeon>) AGrid
                    .getDataProvider();
            Dialog deleteDungeonDialog = new Dialog();
            Text deleteCheckTitle = new Text("Willst du deinen Dungeon '" + ADungeon.getDungeonName() + "' wirklich löschen?");
            Button deleteDungeonButt = new Button("Dungeon löschen");
            deleteDungeonButt.getStyle().set("color", "red");
            Button cancelButt = new Button("Abbrechen");

            HorizontalLayout buttonLayout = new HorizontalLayout(deleteDungeonButt, cancelButt);
            buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
            deleteDungeonDialog.add(deleteCheckTitle, buttonLayout);
            cancelButt.focus();
            deleteDungeonDialog.open();

            deleteDungeonButt.addClickListener(e -> {
                try {
                    dataProvider.getItems().remove(ADungeon);
                    configuratorServiceI.deleteDungeon(ADungeon.getDungeonId());
                    dataProvider.refreshAll();
                    Notification.show("Dungeon '" + ADungeon.getDungeonName() + "' gelöscht");
                } catch (Exception d) {
                    Notification.show("Dungeon '" + ADungeon.getDungeonName() + "' kann nicht gelöscht werden!");
                }
                deleteDungeonDialog.close();
            });
            cancelButt.addClickListener(e -> {
                deleteDungeonDialog.close();
            });
        });

        Icon iconDeleteDungeon = new Icon(VaadinIcon.CLOSE_BIG);
        button.setIcon(iconDeleteDungeon);

        button.getStyle().set("color", "red");
        return button;
    }

    private void initButtonsLayout() {
        buttonsLayout.add(newDungeonButton, editDungeonButton);
    }

    private void initEditDungeonButton() {
        editDungeonButton.addClickListener(e -> {
            if (true || !((Dungeon) dungeonGrid.getSelectedItems().toArray()[0]).getDungeonStatus().equals(Status.ACTIVE)) {//TODO
                if (dungeonGrid.getSelectedItems().size() > 0) {
                    //das ist das hässlichste stück code ever ever
                    UI.getCurrent().navigate("configurator/" + ((Dungeon) dungeonGrid.getSelectedItems().toArray()[0]).getDungeonId());
                } else {
                    Notification.show("Wähle einen Dungeon aus, den du bearbeiten möchtest.\nSofern du noch keinen hast, kannst du einfach einen erstellen.");
                }
            } else {
                Notification.show("Der Dungeon wird gerade bespielt.");
            }
        });
    }

    private void initNewDungeonButton() {
        newDungeonButton.addClickListener(e -> {
            Dungeon dungeon = configuratorServiceI.createDungeon("Mein Dungeon", VaadinSession.getCurrent().getAttribute(User.class), Status.INACTIVE);
            Notification.show("Neuen Dungeon erstellt");
            //TODO param ID hinzufügen
            UI.getCurrent().navigate("configurator/" + dungeon.getDungeonId());
        });
    }
}
