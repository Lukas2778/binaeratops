package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Oberfläche des Tabs 'Eigene Dungeons'
 */
//@Route(value = "myDungeons",layout = MainView.class)
@PageTitle("Eigene Dungeons")
public class MyDungeonsView extends VerticalLayout {
    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Eigene Dungeons'.
     */

    private ListBox dungeonList = new ListBox<String>();
    private Button newDungeonButton = new Button("Dungeon erstellen");
    private Button editDungeonButton = new Button( "Bearbeiten");
    private HorizontalLayout buttonsLayout = new HorizontalLayout();
    private H1 title = new H1("Meine Dungeons");

    public MyDungeonsView() {
        super ();

        initButtonsLayout();
        initnewDungeonButton();
        add(title, buttonsLayout, dungeonList);
    }

    private void initButtonsLayout(){
        buttonsLayout.add(newDungeonButton, editDungeonButton);
    }

    private void initEditDungeonButton(){
        //newDungeonButton.addClickListener();
    }
    private void initnewDungeonButton(){
        newDungeonButton.addClickListener(e ->{
            UI.getCurrent().navigate("configurator");
        });
    }
}
