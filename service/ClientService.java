package service;

import model.*;

import java.util.*;
import java.time.LocalDate;

public class ClientService {
    private List<Client> clients;

    public ClientService(){
        clients = new ArrayList<Client>();
    }

    public List<Client> getClients(){
        return clients;
    }


    // 1.Create new client
    public void createClient(String name, Address address){
        Client client = new Client(name, address);
        clients.add(client);
        System.out.println("Client created: " + client.getName() + " id: " + client.getId());
    }


    private Client findClientById(String id){
        for (Client client : clients){
            if (client.getId().equalsIgnoreCase(id)){
                return client;
            }
        }
        return null;
    }

    // 2.Add account to a client
    public void addAccountToClient(String id, Account account){
        Client client = findClientById(id);
        if (client != null){
            client.getAccounts().add(account);
            System.out.println("Account added to client " + client.getName());
        }
        else {
            System.out.println("Client id not found.");
        }
    }

    // 3. Show all clients
    public void showAllClients(){
        if (clients.isEmpty()){
            System.out.println("No clients found.");
        }
        else{
            for (Client client : clients){
                System.out.println(client);
            }
        }
    }

    // 4. Show all accounts for a client
    public void showAllAccountsForAClient(String id){
        Client client = findClientById(id);
        if (client != null){
            if (client.getAccounts().isEmpty()){
                System.out.println("Client has no accounts.");
            }
            else {
                for (Account account : client.getAccounts()){
                    System.out.println(account);
                }
            }
        }
        else {
            System.out.println("No clients found.");
        }
    }

    // 5. Show a client's account with the greatest balance
    public void showAccountWithMaxBalance(String id){
        Client client = findClientById(id);
        if (client != null){
            double maxBalance = 0;
            Account accountWithMax = null;
            for (Account account : client.getAccounts()){
                if (account.getBalance() > maxBalance){
                    maxBalance = account.getBalance();
                    accountWithMax = account;
                }
            }
            if (accountWithMax != null){
                System.out.println("Account with maximum balance: ");
                System.out.println(accountWithMax);
            }
        }
        else {
            System.out.println("Client not found.");
        }
    }

    // 6. Show a client's active and inactive cards
    public void showActiveInactiveCards(String id){
        Client client = findClientById(id);
        if (client != null){
            boolean found = false;
            for (Account account : client.getAccounts()){
                for (Card card : account.getCards()){
                    found = true;
                    String status = card.isActive() ? "Active" : "Inactive";
                    System.out.println(card.getCardType() + " " + card.getCardNumber() + " - " + status);
                }
            }
            if (!found){
                System.out.println("Client has no cards");
            }
        }
        else {
            System.out.println("Client not found.");
        }
    }

    // 7. Send notification to client
    public void sendNotificationToClient(String id, String message, LocalDate date){
        Client client = findClientById(id);
        if (client != null){
            for (Account account : client.getAccounts()){
                Notification notification = new Notification(message, date);
                account.addNotification(notification);
            }
            System.out.println("Notification sent to client " + client.getName());
        }
        else {
            System.out.println("Client not found.");
        }
    }

    // 8. Show all notifications for a client
    public void showNotificationsForClient(String id){
        Client client = findClientById(id);
        if (client != null){
            for (Account account : client.getAccounts()){
                account.showAllNotifications();
            }
        }
        else {
            System.out.println("Client not found.");
        }
    }
}
