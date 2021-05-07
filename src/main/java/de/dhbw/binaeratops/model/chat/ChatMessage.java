package de.dhbw.binaeratops.model.chat;

import java.time.LocalDateTime;

public class ChatMessage
{
    private String from;
    private LocalDateTime time;
    private String message;

    public ChatMessage(String from, String message){
        this.from = from;
        this.message = message;
        this.time = LocalDateTime.now();
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
}
