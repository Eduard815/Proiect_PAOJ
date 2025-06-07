package model;

import java.time.LocalDate;

import util.NotificationIdGenerator;

public class Notification {
    private String id;
    private String message;
    private LocalDate timestamp;
    private boolean read;
    private String accountId;

    public Notification(String message, LocalDate timestamp){
        this.id = NotificationIdGenerator.generateNotificationId();
        this.message = message;
        this.timestamp = timestamp;
        this.read = false;
    }

    /// Added constructor overload for the variables read and id, taken from the database
    public Notification(String id, String message, LocalDate timestamp, boolean read, String accountId){
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.read = read;
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getId(){
        return id;
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

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return "|" + timestamp + "|" + (read ? "(Read) " : "(Unread) ") + message;
    }
}
