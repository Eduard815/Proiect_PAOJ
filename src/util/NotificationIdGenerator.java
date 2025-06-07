package util;

import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

public class NotificationIdGenerator {
    private static final Set<String> generatedNotificationIds = new HashSet<>();

    public static String generateNotificationId(){
        String id;
        
        do {
            id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (generatedNotificationIds.contains(id));

        generatedNotificationIds.add(id);
        return id;
    }
}
