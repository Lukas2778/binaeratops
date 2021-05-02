package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * Oberfläche des Tabs 'Eigene Dungeons'
 */
//@Route(value = "myDungeons",layout = MainView.class)
@PageTitle("Eigene Dungeons")
public class MyDungeonsView extends VerticalLayout {

    private ListBox<Dungeon> dungeonList = new ListBox<Dungeon>();
    private Button newDungeonButton = new Button("Dungeon erstellen");
    private Button editDungeonButton = new Button( "Bearbeiten");
    private HorizontalLayout buttonsLayout = new HorizontalLayout();
    private H1 title = new H1("Meine Dungeons");


    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Eigene Dungeons'.
     */
    public MyDungeonsView(@Autowired DungeonServiceI configuratorService) {
        super ();

        User user = VaadinSession.getCurrent().getAttribute(User.class);

        dungeonList.setRenderer(new ComponentRenderer<>(dungeon ->{
            Label label = new Label(dungeon.getDungeonName());
            label.getStyle().set("propertyName", "value");
            label.addClassName("itemLabel");
            return label;
        }));

        initButtonsLayout();
        initNewDungeonButton();
        dungeonList.setHeightFull();
        dungeonList.setItems(configuratorService.getAllDungeonsFromUser(user));
        add(title, buttonsLayout, dungeonList);
        setSizeFull();
    }

    private void initButtonsLayout(){
        buttonsLayout.add(newDungeonButton, editDungeonButton);
    }

    private void initEditDungeonButton(){
        newDungeonButton.addClickListener(e->{
            UI.getCurrent().navigate("configurator");
        });
    }
    private void initNewDungeonButton(){
        newDungeonButton.addClickListener(e ->{
            UI.getCurrent().navigate("configurator");
        });
    }
}
