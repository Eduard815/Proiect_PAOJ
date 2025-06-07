package service;

import model.*;
import dao.*;

import java.util.*;
import java.time.LocalDate;

public class ClientService {
    private List<Client> clients;
    private ClientDAO clientDAO;
    private AccountService accountService;
    private NotificationService notificationService;
    private CardService cardService;

    public ClientService(){
        clients = new ArrayList<Client>();
        clientDAO = new ClientDAO();
        clients = clientDAO.getAllClients();
        accountService = new AccountService();
        notificationService = new NotificationService();
        cardService = new CardService();
    }

    public List<Client> getClients(){
        return clients;
    }


    // 1.Create new client
    public void createClient(String name, Address address){
        Client client = new Client(name, address);
        clients.add(client);
        System.out.println("Client created: " + client.getName() + " id: " + client.getId());

        clientDAO.createClient(client);   /// C
    }


    private Client findClientById(String id){
        return clientDAO.getClientById(id);   /// R
    }


    // 2.Add account to a client
    public void addAccountToClient(String id, Account account){
        Client client = findClientById(id);
        if (client != null){
            client.getAccounts().add(account);
            accountService.createAccount(account, id);
            System.out.println("Account added to client " + client.getName());
        }
        else {
            System.out.println("Client id not found.");
        }
    }

    // 3. Show all clients
    public void showAllClients(){
        List<Client>dbClients = clientDAO.getAllClients();   /// R
        if (dbClients.isEmpty()){
            System.out.println("No clients found.");
        } else {
            for (Client client : dbClients){
                List<Account> accounts = accountService.getAccountsForClient(client.getId());
                client.getAccounts().addAll(accounts);
                System.out.println(client);
            }
        }
    }

    // 4. Show all accounts for a client
    public void showAllAccountsForAClient(String id){
        Client client = findClientById(id);
        if (client != null){
            List<Account> accounts = accountService.getAccountsForClient(id);
            if (accounts.isEmpty()){
                System.out.println("Client has no accounts.");
            }
            else {
                for (Account account : accounts){
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
            for (Account account : accountService.getAccountsForClient(id)){
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
            for (Account account : accountService.getAccountsForClient(id)){
                List<Card> cards = cardService.getCardsForAccount(account.getId());
                for (Card card : cards){
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
            for (Account account : accountService.getAccountsForClient(id)){
                Notification notification = new Notification(message, date);
                notificationService.createNotification(notification, account.getId());
                System.out.println("Notification sent to account " + account.getIban());
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
            for (Account account : accountService.getAccountsForClient(id)){
                List<Notification> notifications = notificationService.getNotificationsForAccount(account.getId());
                for (Notification notification : notifications){
                    System.out.println(notification);
                }
            }
        }
        else {
            System.out.println("Client not found.");
        }
    }

    public void updateClient(Client client){ /// U
        clientDAO.updateClient(client);
        System.out.println("Client updated in database.");
    }

    public void deleteClient(String id){     /// D
        clientDAO.deleteClient(id);
        clients.removeIf(client -> client.getId().equalsIgnoreCase(id));
        System.out.println("Client deleted.");
    }
}
