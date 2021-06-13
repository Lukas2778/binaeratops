package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Tab-Oberfläche für die Komponente "Eigene Dungeons" des Hauptmenüs.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für die Eigenen Dungeons eines Benutzers bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
public class MyDungeonsView extends VerticalLayout implements HasDynamicTitle {

    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

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
        newDungeonButton = new Button(res.getString("view.my.dungeons.button.create.dungeon"));
        editDungeonButton = new Button(res.getString("view.my.dungeons.button.edit.dungeon"));
        buttonsLayout = new HorizontalLayout();
        title = new H1(res.getString("view.my.dungeons.headline"));

        User user = VaadinSession.getCurrent().getAttribute(User.class);
        dungeonList.addAll(dungeonServiceI.getAllDungeonsFromUser(user));

        initButtonsLayout();
        initEditDungeonButton();
        initNewDungeonButton();
        dungeonGrid = new Grid<>();
        dungeonGrid.setItems(dungeonList);
        dungeonGrid.setVerticalScrollingEnabled(true);
        dungeonGrid.addColumn(Dungeon::getDungeonName).setHeader(res.getString("view.my.dungeons.grid.dungeonname"));
        dungeonGrid.addColumn(Dungeon::getDescription).setHeader(res.getString("view.my.dungeons.grid.description"));
        dungeonGrid.addColumn(Dungeon::getDungeonVisibility).setHeader(res.getString("view.my.dungeons.grid.visibility"));
        dungeonGrid.addColumn(Dungeon::getDungeonStatus).setHeader(res.getString("view.my.dungeons.grid.status"));
        dungeonGrid.addComponentColumn(dungeon -> {
            Button button = new Button(res.getString("view.my.dungeons.grid.button.start"));
            button.addClickListener(e -> {
                if (dungeon.getDungeonVisibility() == Visibility.IN_CONFIGURATION)
                    Notification.show("Der Dungeon muss auf Public oder Private gesetzt werden");
                else
                    UI.getCurrent().navigate("play/dungeonmaster/" + dungeon.getDungeonId().toString());
                dungeonServiceI.activateDungeon(dungeon.getDungeonId());
            });
            return button;
        }).setHeader(res.getString("view.my.dungeons.grid.start"));
        dungeonGrid.addComponentColumn(item -> createRemoveButton(dungeonGrid, item)).setHeader(res.getString("view.my.dungeons.grid.delete"));


        add(title, buttonsLayout, dungeonGrid);
        setSizeFull();
    }

    // TODO Kommentare schreiben
    private Button createRemoveButton(Grid<Dungeon> AGrid, Dungeon ADungeon) {

        Button button = new Button("", clickEvent -> {
            ListDataProvider<Dungeon> dataProvider = (ListDataProvider<Dungeon>) AGrid
                    .getDataProvider();
            Dialog deleteDungeonDialog = new Dialog();
            Text deleteCheckTitle = new Text(MessageFormat.format(res.getString("view.my.dungeons.text.delete.dungeon.question"), ADungeon.getDungeonName()));
            Button deleteDungeonButt = new Button(res.getString("view.my.dungeons.button.delete.dungeon"));
            deleteDungeonButt.getStyle().set("color", "red");
            Button cancelButt = new Button(res.getString("view.my.dungeons.button.cancel"));

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
                    Notification.show(MessageFormat.format(res.getString("view.my.dungeons.notification.delete.success"), ADungeon.getDungeonName()));
                } catch (Exception d) {
                    Notification.show(MessageFormat.format(res.getString("view.my.dungeons.notification.delete.failure"), ADungeon.getDungeonName()));
                }
                deleteDungeonDialog.close();
            });
            cancelButt.addClickListener(e -> {
                deleteDungeonDialog.close();
            });
        });

        Icon iconDeleteDungeon = new Icon(VaadinIcon.CLOSE_BIG);
        button.setIcon(iconDeleteDungeon);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

//        button.getStyle().set("color", "red");
        return button;
    }

    private void initButtonsLayout() {
        buttonsLayout.add(newDungeonButton, editDungeonButton);
    }

    private void initEditDungeonButton() {
        editDungeonButton.addClickListener(e -> {
            if (dungeonGrid.getSelectedItems().size() > 0) {
                if (!((Dungeon) dungeonGrid.getSelectedItems().toArray()[0]).getDungeonStatus().equals(Status.ACTIVE)) {
                    //das ist das hässlichste stück code ever ever
                    UI.getCurrent().navigate("configurator/" + ((Dungeon) dungeonGrid.getSelectedItems().toArray()[0]).getDungeonId());
                } else {
                    Notification.show(res.getString("view.my.dungeons.notification.ingame"));
                }
            } else {
                Notification.show(res.getString("view.my.dungeons.notification.select.edit"));
            }
        });
    }

    private void initNewDungeonButton() {
        newDungeonButton.addClickListener(e -> {
            Dungeon dungeon = configuratorServiceI.createDungeon(res.getString("view.my.dungeons.dungeonname.preset"), VaadinSession.getCurrent().getAttribute(User.class), Status.INACTIVE);
            Notification.show(res.getString("view.my.dungeons.notification.dungeon.created"));
            //TODO param ID hinzufügen
            UI.getCurrent().navigate("configurator/" + dungeon.getDungeonId());
        });
        newDungeonButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.main.tab.my.dungeons");
    }
}
