package util;

import java.util.UUID;

public class AccountIdGenerator {
    public static String generateId(){
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}