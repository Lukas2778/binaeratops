package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
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
    UserRepositoryI userRepositoryI;


    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Eigene Dungeons'.
     * @param ADungeonService KonfiguratorService.
     */
    public MyDungeonsView(@Autowired DungeonServiceI ADungeonService, @Autowired ConfiguratorServiceI AConfiguratorService, @Autowired UserRepositoryI AUserRepository) {
        dungeonServiceI=ADungeonService;
        configuratorServiceI=AConfiguratorService;
        userRepositoryI=AUserRepository;

        dungeonList = new ArrayList<>();
        newDungeonButton = new Button("Dungeon erstellen");
        editDungeonButton = new Button( "Bearbeiten");
        buttonsLayout = new HorizontalLayout();
        title = new H1("Meine Dungeons");

        User user = VaadinSession.getCurrent().getAttribute(User.class);
        dungeonList.addAll(dungeonServiceI.getAllDungeonsFromUser(user));

        initButtonsLayout();
        initNewDungeonButton();
        Grid<Dungeon> dungeonGrid=new Grid<>();
        dungeonGrid.setItems(dungeonList);
        dungeonGrid.addColumn(Dungeon::getDungeonName).setHeader("Name");
        dungeonGrid.addColumn(Dungeon::getDescription).setHeader("Beschreibung");
        dungeonGrid.addColumn(Dungeon::getDungeonStatus).setHeader("Status");

//        dungeonList.setHeightFull();

        add(title, buttonsLayout, dungeonGrid);
        setSizeFull();
    }

    private void initButtonsLayout(){
        buttonsLayout.add(newDungeonButton, editDungeonButton);
    }

//    private void initEditDungeonButton(){
//        newDungeonButton.addClickListener(e->{
//            UI.getCurrent().navigate("configurator");
//        });
//    }
    private void initNewDungeonButton(){
        newDungeonButton.addClickListener(e ->{
            configuratorServiceI.createDungeon("Neuer Dungeon", VaadinSession.getCurrent().getAttribute(User.class));
            Notification.show("Neuer Dungeon erstellt");
            UI.getCurrent().navigate("configurator");
        });
    }
}
