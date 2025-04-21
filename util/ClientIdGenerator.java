package util;

import java.util.UUID;

public class ClientIdGenerator {
    public static String generateId(){
        return "CL" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }
}
