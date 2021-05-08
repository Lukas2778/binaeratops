package de.dhbw.binaeratops.view.mainviewtabs;

import com.awesomecontrols.subwindow.SubWindow;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.chat.MessageList;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.chat.PlayerServiceI;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

/**
 * Oberfläche des Tabs 'Über uns'
 */
//@Route(value = "aboutUs",layout = MainView.class)

//@PreserveOnRefresh

@PageTitle("Über Uns")



public class AboutUsView extends VerticalLayout {
    H1 binTitle;
    String aboutText;
    Html html;

    MessageList messageList;
    PlayerServiceI playerService;

    private final Flux<ChatMessage> messages;
    private final PlayerServiceI APlayerService;
    private final UnicastProcessor publisher;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     */
    public AboutUsView(@Autowired PlayerServiceI APlayerService, UnicastProcessor<ChatMessage> publisher, Flux<ChatMessage> messages) {

        this.APlayerService = APlayerService;
        this.publisher = publisher;
        this.messages = messages;

        binTitle=new H1("Willkommen bei Binäratops");
        add(binTitle);

        showChat();



        setSizeFull();

    }


    private void showChat()
    {
        createInputLayout();
        messageList = new MessageList();

       SubWindow sw = new SubWindow("sw1");

       sw.setHeight("150px");
      sw.setWidth("300px");

        add(sw, messageList,  createInputLayout() );

        expand(messageList);


        messages.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> {
            if(message.getUserIdList().contains(VaadinSession.getCurrent().getAttribute(User.class).getUserId()) )
            {
                messageList.add(new Paragraph(message.getFrom() + ": " + message.getMessage()));
            }
        })

        ));
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
            List<Long> userIdList = new ArrayList<>();
            userIdList.add(23L);
            //Parser erkennt das es ne nachricht ist

            publisher.onNext(new ChatMessage(VaadinSession.getCurrent().getAttribute(User.class).getName(), messageField.getValue(), userIdList ));
//            playerService.sendInput(messageField.getValue(), VaadinSession.getCurrent().getAttribute(User.class));
            //Das war Quatsch
          messageField.clear();
           messageField.focus();
        });


        flüsternButton.addClickListener(click -> {
            messageList.add(new Paragraph(messageField.getValue()) );
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

//    @EventListener(UpdateChatEvent.class)
//    public void handleNewMessage(UpdateChatEvent AEvent){
//
//        getUI().ifPresent(ui -> ui.access(() -> {
//            messageList.add(new Paragraph(AEvent.getMessage()));
//        }));
//
//    }


}
