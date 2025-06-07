package service;

import model.*;
import dao.*;

import java.util.List;

public class NotificationService {
    private NotificationDAO notificationDAO;

    public NotificationService(){
        notificationDAO = new NotificationDAO();
    }

    public List<Notification> getNotificationsForAccount(String accountId){
        return notificationDAO.getAllNotifications().stream()
                .filter(n -> n.getAccountId() != null && n.getAccountId().equals(accountId))
                .toList();
    }


    /// C
    public void createNotification(Notification notification, String accountId){
        notificationDAO.createNotification(notification, accountId);
        System.out.println("Notification created in database.");
    }

    /// R
    public void showAllNotifications(){
        List<Notification>notifications = notificationDAO.getAllNotifications();
        if (notifications.isEmpty()){
            System.out.println("No notification found.");
        }
        else {
            for (Notification notification : notifications){
                System.out.println(notification);
            }
        }
    }

    public Notification getNotificationById(String id){
        return notificationDAO.getNotificationById(id);
    }

    /// U
    public void updateNotification(Notification notification){
        notificationDAO.updateNotification(notification, notification.getId());
        System.out.println("Notification updated in database.");
    }

    /// D
    public void deleteNotification(String id){
        notificationDAO.deleteNotification(id);
        System.out.println("Notification deleted from databases");
    }
}
