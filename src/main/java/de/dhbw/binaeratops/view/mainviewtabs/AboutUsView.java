package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.chat.MessageList;
import de.dhbw.binaeratops.service.events.UpdateChatEvent;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.chat.PlayerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * Oberfläche des Tabs 'Über uns'
 */
//@Route(value = "aboutUs",layout = MainView.class)
@PageTitle("Über Uns")
@org.springframework.stereotype.Component
public class AboutUsView extends VerticalLayout {
    H1 binTitle;
    String aboutText;
    Html html;

    MessageList messageList;
    PlayerServiceI playerService;


    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     */
    public AboutUsView(@Autowired PlayerServiceI APlayerService) {

        this.playerService = APlayerService;

        binTitle=new H1("Willkommen bei Binäratops");
        add(binTitle);

        showChat();



        setSizeFull();

    }


    private void showChat()
    {
        createInputLayout();
        messageList = new MessageList();

        add(messageList, createInputLayout());

        expand(messageList);
    }

    TextField messageField;

    private Component createInputLayout()
    {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button flüsternButton = new Button("Flüstern");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(messageField, sendButton, flüsternButton);
        layout.expand(messageField);

        sendButton.addClickListener(click -> {
            playerService.sendInput(messageField.getValue(), VaadinSession.getCurrent().getAttribute(User.class));
          messageField.clear();
           messageField.focus();
        });


        flüsternButton.addClickListener(click -> {
            messageField.clear();
            messageField.focus();
        });
        messageField.focus();

        return layout;
    }

    /**
     * Wenn der Chat des Spielers eine neue Nachricht erhält wird dieses Event ausgelöst.
     * Der Chat wird mit der Nachricht ergänzt.
     * @param AEvent Die Nachricht.
     */
    @Async
    @EventListener(UpdateChatEvent.class)
    void handleNewMessage(UpdateChatEvent AEvent){
        messageList.add(new Paragraph(AEvent.getMessage()));
    }
}
