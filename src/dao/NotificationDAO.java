package dao;

import model.Notification;
import util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    private final Connection connection;
    public NotificationDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void createNotification(Notification notification, String accountId){
        String sql = "INSERT INTO notifications (id, message, date, read, account_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, notification.getId());
            statement.setString(2, notification.getMessage());
            statement.setDate(3, Date.valueOf(notification.getTimestamp()));
            statement.setBoolean(4, notification.getRead());
            statement.setString(5, accountId);

            statement.executeUpdate();
            System.out.println("Notification created successfully.");
        }
        catch (SQLException e){
            System.out.println("Error creating notification: " + e.getMessage());
        }
    }


    public List<Notification> getAllNotifications(){
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                String dbId = rs.getString("id");
                String message = rs.getString("message");
                LocalDate date = rs.getDate("date").toLocalDate();
                boolean read = rs.getBoolean("read");
                String accountId = rs.getString("account_id");

                Notification notification = new Notification(dbId, message, date, read, accountId);
                notifications.add(notification);
            }
        }
        catch (SQLException e){
            System.out.println("Error retrieving notifications: " + e.getMessage());
        }
        return notifications;
    }


    public Notification getNotificationById(String id){
        String sql = "SELECT * FROM notifications WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                String dbId = rs.getString("id");
                String message = rs.getString("message");
                LocalDate date = rs.getDate("date").toLocalDate();
                boolean read = rs.getBoolean("read");
                String accountId = rs.getString("account_id");

                Notification notification = new Notification(dbId, message, date, read, accountId);
                return notification;
            }
        }
        catch (SQLException e){
            System.out.println("Error retrieving notification: " + e.getMessage());
        }
        return null;
    }


    public void updateNotification(Notification notification, String id){
        String sql = "UPDATE notifications SET message = ?, date = ?, read = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, notification.getMessage());
            statement.setDate(2, Date.valueOf(notification.getTimestamp()));
            statement.setBoolean(3, notification.getRead());
            statement.setString(4, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0){
                System.out.println("Notification updated successfully.");
            }
            else {
                System.out.println("No notification found with id " + id);
            }
        }
        catch (SQLException e){
            System.out.println("Error updating notification: " + e.getMessage());
        }
    }


    public void deleteNotification(String id){
        String sql = "DELETE FROM notifications WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, id);
            int rows = statement.executeUpdate();

            if (rows > 0){
                System.out.println("Notification deleted successfully.");
            }
            else {
                System.out.println("No notification found with id " + id);
            }
        }
        catch (SQLException e){
            System.out.println("Error deleting notification: " + e.getMessage());
        }
    }

}
