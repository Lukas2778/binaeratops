package de.dhbw.binaeratops.service.impl.chat;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.UnicastProcessor;

import java.util.ArrayList;
import java.util.List;


/**
 * Komponente "ChatService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Erstellen und Versenden von Chatnachrichten bereit.
 * </p>
 * <p>
 * Für Schnittstelle dieser Komponente siehe {@link ChatServiceI}.
 * </p>
 *
 * @author Timon Gartung, Pedro Treuer
 */
@Service
@CssImport("./views/game/game-view.css")
public class ChatService implements ChatServiceI {

    @Autowired
    private UnicastProcessor publisher;

    /**
     * Sendet eine Nachricht ohne Absender an alle angegebenen Benutzern.
     *
     * @param AMessage      Nachricht.
     * @param AReceiverList Liste von Empfängern der Nachricht.
     */
    @Override
    public void sendMessage(String AMessage, List<User> AReceiverList) {
        List<Long> receiverList = convertToUserIDList(AReceiverList);
        Label sender = new Label("Aktion: " + AMessage);
        sender.addClassName("boldtext");
        Paragraph paragraph = new Paragraph(sender);
        sendChatMessage(new ChatMessage(paragraph, AMessage, receiverList));
    }

    @Override
    public void sendActionMessage(String AMessage, User AReceiver) {
        Label mess = new Label(AMessage);
        Label action= new Label("Aktion: ");
        action.addClassName("actionColor");
        mess.addClassName("boldtext");
        Paragraph paragraph = new Paragraph(action, mess);
        sendChatMessage(new ChatMessage(paragraph, AMessage, AReceiver.getUserId()));
    }

    @Override
    public void notifyAll(String AMessage, List<User> AReceiverList, User ADungeonMaster) {
        List<Long> receiverList = convertToUserIDList(AReceiverList);
        Label sender = new Label("Dungeon-Master~ALL~" + ADungeonMaster.getName() + ":");
        sender.addClassName("dmnamecolor");
        Paragraph paragraph = buildParagraph(sender, AMessage);
        sendChatMessage(new ChatMessage(paragraph, AMessage, receiverList));
    }

    @Override
    public void whisperDungeonMaster(String AMessage, User AReceiver, User ADungeonMaster) {
        Label sender = new Label("Dungeon-Master~" + ADungeonMaster.getName() + ":");
        sender.addClassName("dmnamecolor");
        Paragraph paragraph = buildParagraph(sender, AMessage);
        sendChatMessage(new ChatMessage(paragraph, AMessage, AReceiver.getUserId()));
    }

    @Override
    public void whisper(String AMessage, User AReceiver, Avatar AAvatar) {
        Label sender = new Label(AAvatar.getName() + ":");
        sender.addClassName("playernamecolor");
        Paragraph paragraph = buildParagraph(sender, AMessage);
        sendChatMessage(new ChatMessage(paragraph, AMessage, AReceiver.getUserId()));
    }

    @Override
    public void whisperDungeonMasterRoom(String AMessage, User AReceiver, User ADungeonMaster, String ARoom) {
        Label sender = new Label(ARoom + "~" + "Dungeon-Master" + ":");
        sender.addClassName("dmnamecolor");
        Paragraph paragraph = buildParagraph(sender, AMessage);
        sendChatMessage(new ChatMessage(paragraph, AMessage, AReceiver.getUserId()));
    }

    @Override
    public void whisperRoom(String AMessage, User AReceiver, Avatar AAvatar, String ARoom) {
        Label sender = new Label(ARoom + "~" +  AAvatar.getName() + ":");
        sender.addClassName("roomnamecolor");
        Paragraph paragraph = buildParagraph(sender, AMessage);
        sendChatMessage(new ChatMessage(paragraph, AMessage, AReceiver.getUserId()));
    }

    @Override
    public void whisperFromNpc(String AMessage, User AReceiver, String AAvatarName) {
        Label sender = new Label("NPC~" + AAvatarName + ":");
        sender.addClassName("npcnamecolor");
        Paragraph paragraph = buildParagraph(sender, AMessage);
        sendChatMessage(new ChatMessage(paragraph, AMessage, AReceiver.getUserId()));
    }

    @Override
    public void sendRoomMessage(String AMessage, List<User> AReceiverList, Avatar AAvatar, Room ARoom) {
        List<Long> receiverList = convertToUserIDList(AReceiverList);
        Label sender = new Label(ARoom.getRoomName() + "~" + AAvatar.getName() + ":");
        sender.addClassName("roomnamecolor");
        Paragraph paragraph = buildParagraph(sender, AMessage);
        sendChatMessage(new ChatMessage(paragraph, AMessage, receiverList));
    }

    @Override
    public void sendRoomMessage(String AMessage, List<User> AReceiverList, User ADungeonMaster, Room ARoom) {
        List<Long> receiverList = convertToUserIDList(AReceiverList);
        Label sender = new Label(ARoom.getRoomName() + "~" + "Dungeon-Master~" + ADungeonMaster.getName() + ":");
        sender.addClassName("roomnamecolor");
        Paragraph paragraph = buildParagraph(sender, AMessage);
        sendChatMessage(new ChatMessage(paragraph, AMessage, receiverList));
    }

    /**
     * Builds the Paragraph for the Chat.
     *
     * @param ASender  Absender der Nachricht als Label.
     * @param AMessage Nachricht.
     * @return Fertiger Paragraph.
     */
    private Paragraph buildParagraph(Label ASender, String AMessage) {
        Html message = new Html("<div>" + AMessage + "</div>");
        return new Paragraph(ASender, message);
    }

    /**
     * Konvertiert eine Liste von Benutzern zu einer Liste mit IDs.
     *
     * @param AList Liste mit Benutzern.
     * @return Liste mit IDs.
     */
    private List<Long> convertToUserIDList(List<User> AList) {
        List<Long> list = new ArrayList<>();
        AList.forEach(user -> list.add(user.getUserId()));
        return list;
    }

    /**
     * Verschickt die Nachricht an alle Benutzer, die einen Chat offen haben.
     *
     * @param message Nachricht.
     */
    private void sendChatMessage(ChatMessage message) {
        publisher.onNext(message);
    }

}
