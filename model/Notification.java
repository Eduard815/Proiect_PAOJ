package model;

import java.time.LocalDate;

public class Notification {
    private String message;
    private LocalDate timestamp;
    private boolean read;

    public Notification(String message, LocalDate timestamp){
        this.message = message;
        this.timestamp = timestamp;
        this.read = false;
    }

    public String getMessage(){
        return message;
    }

    public LocalDate getTimestamp(){
        return timestamp;
    }

    public boolean getRead(){
        return read;
    }

    public void markAsRead(){
        read = true;
    }

    @Override
    public String toString(){
        return "|" + timestamp + "|" + (read ? "(Read) " : "(Unread) ") + message;
    }
}
