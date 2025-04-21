package util;

import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

public class TransactionIdGenerator {
    private static final Set<String> generatedTransactionIds = new HashSet<>();

    public static String generateTransactionId(){
        String id;
        
        do {
            id = "TX" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (generatedTransactionIds.contains(id));

        generatedTransactionIds.add(id);
        return id;
    }
}
