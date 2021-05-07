package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.dhbw.binaeratops.model.chat.MessageList;

/**
 * Oberfläche des Tabs 'Über uns'
 */
//@Route(value = "aboutUs",layout = MainView.class)
@PageTitle("Über Uns")
public class AboutUsView extends VerticalLayout {

    H1 binTitle;
    String aboutText;
    Html html;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     */
    public AboutUsView() {
        binTitle=new H1("Willkommen bei Binäratops");
        add(binTitle);

        showChat();



        setSizeFull();

    }
    public void showChat()
    {
        createInputLayout();
        MessageList messageList = new MessageList();

        add(messageList, createInputLayout());

        expand(messageList);

       // messages.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> messageList.add(new Paragraph(
       //         message.getFrom() + ": " + message.getMessage())))

       // ));

        //messages2.subscribe(message2 -> getUI().ifPresent(ui -> ui.access(() -> messageList2.add(new Paragraph(
        //        message2.getFrom() + " flüstert: " + message2.getMessage())))

        //));
    }

    private Component createInputLayout()
    {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button flüsternButton = new Button("Flüstern");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(messageField, sendButton, flüsternButton);
        layout.expand(messageField);

        sendButton.addClickListener(click -> {
//            publisher.onNext(new ChatMessage(username, messageField.getValue()));
//          messageField.clear();
           messageField.focus();
        });


        flüsternButton.addClickListener(click -> {
          //  publisher2.onNext(new ChatMessage(username, messageField.getValue()));
            messageField.clear();
            messageField.focus();
        });
        messageField.focus();

        return layout;
    }
}
