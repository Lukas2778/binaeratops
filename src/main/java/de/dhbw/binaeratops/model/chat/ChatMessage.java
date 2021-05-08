package de.dhbw.binaeratops.model.chat;

import de.dhbw.binaeratops.model.entitys.User;
import java.time.LocalDateTime;
import java.util.List;

public class ChatMessage
{
    private String from;
    private LocalDateTime time;
    private String message;
    private List<Long> userIdList;



    public ChatMessage(String from, String message, List<Long> AUserIdList){
        this.from = from;
        this.message = message;
        this.time = LocalDateTime.now();
        this.userIdList = AUserIdList;
    }
    public ChatMessage(String from, String message1, String message2){
        this.from = from;
        this.message = message1;
        this.message = message2;
        this.time = LocalDateTime.now();
    }
    public String getFrom() {return from; }

    public LocalDateTime getTime() {return time; }


    public String getMessage(){return message;}
    public String getMessages(){return message ;}
    public List<Long> getUserIdList()
    {
        return userIdList;
    }
}
