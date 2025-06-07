package service;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AuditService {
    private static AuditService instance;
    private PrintWriter writer;

    private AuditService(){
        try {
            writer = new PrintWriter(new FileWriter("audit.csv", true));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static AuditService getInstance(){
        if (instance == null){
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String actionName){
        String timestamp = java.time.LocalDateTime.now().toString();
        writer.println(actionName + "," + timestamp);
        writer.flush();
    }

    public void close(){
        if (writer != null){
            writer.close();
        }
    }
}
