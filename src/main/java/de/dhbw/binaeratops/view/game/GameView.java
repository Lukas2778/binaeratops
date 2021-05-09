package de.dhbw.binaeratops.view.game;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Oberfläche des Tabs 'Über uns'
 */
@Route(value = "game")
@PageTitle("Dungeon - Spiel")
public class GameView extends VerticalLayout implements HasUrlParameter<Long> {
    Long dungeonId;

    H2 binTitle;
    String aboutText;
    Html html;
    //TODO: Textausgabebereich

    HorizontalLayout insertInputLayout;
    TextField textField;
    Button confirmButt;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     */
    public GameView() {
        binTitle=new H2("Du bist in der Spieloberfläche!");
        aboutText= "<div>Du hast auf einen aktiven Dungeon geklickt und kannst hier Teile des Chats und des Parsers" +
                " testen.<br>Schau dir zuerst die 'Help' an, indem du /help eingibst.</div>";
        html=new Html(aboutText);

        textField=new TextField();
        textField.focus();
        confirmButt=new Button("Eingabe");
        confirmButt.addClickShortcut(Key.ENTER);
        insertInputLayout=new HorizontalLayout();
        insertInputLayout.add(textField, confirmButt);

        setSizeFull();
        add(binTitle, html, insertInputLayout);
    }

    @Override
    public void setParameter(BeforeEvent ABeforeEvent, Long ALong) {
        this.dungeonId=ALong;
    }
}