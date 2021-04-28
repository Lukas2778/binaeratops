package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Oberfläche des Tabs 'Eigene Dungeons'
 */
//@Route(value = "myDungeons",layout = MainView.class)
@PageTitle("Eigene Dungeons")
public class MyDungeonsView extends HorizontalLayout {
    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Eigene Dungeons'.
     */

    private ListBox l;

    public MyDungeonsView() {
        super ();

//        com.vaadin.flow.component.html.H1 l = new H1("Coming Soon ...");
//        this.add ( l );
//
//        this.setSizeFull ();
//        this.setJustifyContentMode ( FlexComponent.JustifyContentMode.CENTER ); // Put content in the middle horizontally.
//        this.setDefaultVerticalComponentAlignment ( FlexComponent.Alignment.CENTER ); // Put content in the middle vertically.
    }
}
