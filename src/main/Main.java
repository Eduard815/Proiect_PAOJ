package main;

import model.*;
import service.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        ClientService clientService = new ClientService();
        AccountService accountService = new AccountService();
        CardService cardService = new CardService();
        TransactionService transactionService = new TransactionService();
        CreditService creditService = new CreditService();
        NotificationService notificationService = new NotificationService();

        boolean running = true;

        while (running){
            System.out.println("\n=========== BANKING APP MENU ===========");
            System.out.println("1. Create new client");
            System.out.println("2. Add account to client");
            System.out.println("3. Show all clients");
            System.out.println("4. Show client accounts");
            System.out.println("5. Show account with max balance");
            System.out.println("6. Show client's active/inactive cards");
            System.out.println("7. Send notification to client");
            System.out.println("8. Show client's notifications");
            System.out.println("9. Transfer between accounts");
            System.out.println("10. Check inactive accounts");
            System.out.println("11. Generate account statement");
            System.out.println("12. Search transactions after date");
            System.out.println("13. Show debit card payments");
            System.out.println("14. Increase overdraft limit");
            System.out.println("15. Generate debit card");
            System.out.println("16. Generate credit card");
            System.out.println("17. Activate/deactivate card");
            System.out.println("18. Make a transaction");
            System.out.println("19. Search transactions on exact date");
            System.out.println("20. Repay credit");
            System.out.println("21. Update client");
            System.out.println("22. Delete client");
            System.out.println("23. Update account");
            System.out.println("24. Delete account");
            System.out.println("25. Update transaction");
            System.out.println("26. Delete transaction");
            System.out.println("27. Update notification");
            System.out.println("28. Delete notification");
            System.out.println("29. Update card");
            System.out.println("30. Delete card");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option){
                case 1 -> {
                    System.out.print("Client name: ");
                    String name = sc.nextLine();
                    System.out.print("City: ");
                    String city = sc.nextLine();
                    System.out.print("Street: ");
                    String street = sc.nextLine();
                    System.out.print("Street number: ");
                    int number = Integer.parseInt(sc.nextLine());
                    System.out.print("Postal code: ");
                    String postal = sc.nextLine();
                    Address address = new Address(city, street, number, postal);
                    clientService.createClient(name, address);
                    AuditService.getInstance().logAction("create_client");
                }

                case 2 -> {
                    System.out.print("Client ID: ");
                    String clientId = sc.nextLine();
                    Client client = findClientById(clientService, clientId);
                    if (client == null){
                        System.out.println("Client not found.");
                        break;
                    }
                    System.out.print("Initial balance: ");
                    double balance = Double.parseDouble(sc.nextLine());
                    System.out.print("Account type (1 = Current, 2 = Savings): ");
                    int type = Integer.parseInt(sc.nextLine());
                    Account acc;
                    if (type == 1){
                        System.out.print("Overdraft limit: ");
                        double overdraft = Double.parseDouble(sc.nextLine());
                        acc = new CurrentAccount(balance, LocalDate.now(), overdraft);
                    }
                    else {
                        System.out.print("Minimum balance: ");
                        double minBalance = Double.parseDouble(sc.nextLine());
                        System.out.print("Annual interest rate: ");
                        double interest = Double.parseDouble(sc.nextLine());
                        acc = new SavingsAccount(balance, LocalDate.now(), minBalance, interest, false);
                    }
                    clientService.addAccountToClient(clientId, acc);
                    AuditService.getInstance().logAction("add_account_to_client");
                }

                case 3 -> {
                    clientService.showAllClients();
                    AuditService.getInstance().logAction("show_all_clients");
                }

                case 4 -> {
                    System.out.print("Client ID: ");
                    clientService.showAllAccountsForAClient(sc.nextLine());
                    AuditService.getInstance().logAction("show_client_accounts");
                }

                case 5 -> {
                    System.out.print("Client ID: ");
                    clientService.showAccountWithMaxBalance(sc.nextLine());
                    AuditService.getInstance().logAction("show_account_with_maximum_balance");
                }

                case 6 -> {
                    System.out.print("Client ID: ");
                    clientService.showActiveInactiveCards(sc.nextLine());
                    AuditService.getInstance().logAction("show_client_active_inactive_cards");
                }

                case 7 -> {
                    System.out.print("Client ID: ");
                    String id = sc.nextLine();
                    System.out.print("Notification message: ");
                    String msg = sc.nextLine();
                    clientService.sendNotificationToClient(id, msg, LocalDate.now());
                    AuditService.getInstance().logAction("send_notification_to_a_client");
                }

                case 8 -> {
                    System.out.print("Client ID: ");
                    clientService.showNotificationsForClient(sc.nextLine());
                    AuditService.getInstance().logAction("show_client_notifications");
                }

                case 9 -> {
                    System.out.print("Sender IBAN: ");
                    Account sender = findAccountByIban(accountService, sc.nextLine());
                    System.out.print("Receiver IBAN: ");
                    Account receiver = findAccountByIban(accountService, sc.nextLine());

                    if (sender == null || receiver == null){
                        System.out.println("Sender or receiver account not found.");
                        break;
                    }

                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(sc.nextLine());
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    accountService.transferBetweenAccounts(sender, receiver, amount, desc, LocalDate.now());
                    AuditService.getInstance().logAction("transfer_between_accounts");
                }

                case 10 -> {
                    System.out.print("Client ID: ");
                    Client client = findClientById(clientService, sc.nextLine());
                    if (client == null){
                        System.out.println("Client not found.");
                    }
                    else {
                        accountService.checkForInactiveAccounts(client.getId());
                    }
                    AuditService.getInstance().logAction("check_inactive_accounts");
                }


                case 11 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc == null){
                        System.out.println("Account not found.");
                        break;
                    }
                    System.out.print("Start date (yyyy-mm-dd): ");
                    LocalDate start = LocalDate.parse(sc.nextLine());
                    System.out.print("End date (yyyy-mm-dd): ");
                    LocalDate end = LocalDate.parse(sc.nextLine());
                    accountService.generateAccountStatement(acc, start, end);
                    AuditService.getInstance().logAction("generate_account_statement");
                }

                case 12 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc == null){
                        System.out.println("Account not found.");
                        break;
                    }
                    System.out.print("Date (yyyy-mm-dd): ");
                    accountService.searchForTransactionsAfterDate(acc, LocalDate.parse(sc.nextLine()));
                    AuditService.getInstance().logAction("search_transactions_after_date");
                }

                case 13 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc != null) accountService.showDebitPayments(acc);
                    else System.out.println("Account not found.");
                    AuditService.getInstance().logAction("show_debit_card_payments");
                }

                case 14 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc != null){
                        System.out.print("Amount to increase: ");
                        double amount = Double.parseDouble(sc.nextLine());
                        accountService.increaseOverdraftLimitForAccount(acc, amount);
                    }
                    else System.out.println("Account not found.");
                    AuditService.getInstance().logAction("increase_overdraft_limit");
                }

                case 15 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc != null){
                        System.out.print("Expiration date (yyyy-mm-dd): ");
                        LocalDate exp = LocalDate.parse(sc.nextLine());
                        System.out.print("CCV: ");
                        int ccv = Integer.parseInt(sc.nextLine());
                        System.out.print("Card owner: ");
                        String owner = sc.nextLine();
                        cardService.generateDebitCard(acc, exp, ccv, owner);
                    }
                    else System.out.println("Account not found.");
                    AuditService.getInstance().logAction("generate_debit_card");
                }

                case 16 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc != null){
                        System.out.print("Expiration date (yyyy-mm-dd): ");
                        LocalDate exp = LocalDate.parse(sc.nextLine());
                        System.out.print("CCV: ");
                        int ccv = Integer.parseInt(sc.nextLine());
                        System.out.print("Credit limit: ");
                        double limit = Double.parseDouble(sc.nextLine());
                        System.out.print("Interest rate: ");
                        double rate = Double.parseDouble(sc.nextLine());
                        System.out.print("Card owner: ");
                        String owner = sc.nextLine();
                        cardService.generateCreditCard(acc, exp, ccv, owner, limit, rate);
                    }
                    else System.out.println("Account not found.");
                    AuditService.getInstance().logAction("generate_credit_card");
                }

                case 17 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc != null){
                        System.out.print("Card number: ");
                        String cardNr = sc.nextLine();
                        System.out.print("Activate? (enter true). Deactivate? (enter false) ");
                        boolean activate = Boolean.parseBoolean(sc.nextLine());
                        cardService.deactivateReactivateCard(acc, cardNr, activate);
                    }
                    else System.out.println("Account not found.");
                    AuditService.getInstance().logAction("activate_deactivate_card");
                }

                case 18 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc == null){
                        System.out.println("Account not found.");
                        break;
                    }
                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(sc.nextLine());
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Type (1=DEPOSIT, 2=WITHDRAWAL, 3=CARD_PAYMENT): ");
                    int t = Integer.parseInt(sc.nextLine());
                    TransactionType type = switch (t){
                        case 1 -> TransactionType.DEPOSIT;
                        case 2 -> TransactionType.WITHDRAWAL;
                        case 3 -> TransactionType.CARD_PAYMENT;
                        default -> null;
                    };
                    if (type != null) transactionService.performTransaction(acc, amount, desc, type, LocalDate.now());
                    else System.out.println("Invalid type.");
                    AuditService.getInstance().logAction("make_a_transaction");
                }

                case 19 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc == null){
                        System.out.println("Account not found.");
                        break;
                    }
                    System.out.print("Date (yyyy-mm-dd): ");
                    transactionService.findTransactionsMadeOnDate(acc, LocalDate.parse(sc.nextLine()));
                    AuditService.getInstance().logAction("search_transactions_on_exact_date");
                }

                case 20 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(accountService, sc.nextLine());
                    if (acc != null){
                        System.out.print("Repayment amount: ");
                        double amount = Double.parseDouble(sc.nextLine());
                        System.out.print("Description: ");
                        String desc = sc.nextLine();
                        creditService.repayCreditForCard(acc, amount, desc, LocalDate.now());
                    }
                    else System.out.println("Account not found.");
                    AuditService.getInstance().logAction("repay_credit");
                }

                case 21 -> {
                    System.out.print("Client ID: ");
                    Client client = findClientById(clientService, sc.nextLine());
                    if (client != null){
                        System.out.print("New Client Name: ");
                        String newName = sc.nextLine();
                        client.setName(newName);
                        clientService.updateClient(client);
                    }
                    else {
                        System.out.println("Client not found.");
                    }
                }

                case 22 -> {
                    System.out.print("Client id to delete: ");
                    String id = sc.nextLine();
                    clientService.deleteClient(id);
                }

                case 23 -> {
                    System.out.print("Account IBAN: ");
                    Account account = findAccountByIban(accountService, sc.nextLine());
                    if (account != null){
                        System.out.print("New balance: ");
                        double newBalance = Double.parseDouble(sc.nextLine());
                        account.setBalance(newBalance);
                        accountService.updateAccount(account);
                    }
                    else {
                        System.out.println("Account not found");
                    }
                }

                case 24 -> {
                    System.out.print("Account id to delete: ");
                    String accId = sc.nextLine();
                    accountService.deleteAccount(accId);
                }

                case 25 -> {
                    System.out.print("Transaction id: ");
                    String tId = sc.nextLine();
                    Transaction t = transactionService.getTransactionById(tId);
                    if (t != null){
                        System.out.print("New description: ");
                        String desc = sc.nextLine();
                        t.setDescription(desc);
                        transactionService.updateTransaction(t);
                    }
                    else {
                        System.out.println("Transaction not found.");
                    }
                }

                case 26 -> {
                    System.out.print("Transaction id to delete: ");
                    String tId = sc.nextLine();
                    transactionService.deleteTransaction(tId);
                }

                case 27 -> {
                    System.out.print("Notification id: ");
                    String nId = sc.nextLine();
                    Notification notification = notificationService.getNotificationById(nId);
                    if (notification != null){
                        System.out.print("New message: ");
                        String message = sc.nextLine();
                        notification.setMessage(message);
                        notificationService.updateNotification(notification);
                    }
                    else {
                        System.out.println("Notification not found");
                    }
                }

                case 28 -> {
                    System.out.print("Notification id to delete:");
                    String nId = sc.nextLine();
                    notificationService.deleteNotification(nId);
                }

                case 29 -> {
                    System.out.print("Card number: ");
                    String cardNr = sc.nextLine();
                    Card card = cardService.getCardByNumber(cardNr);
                    if (card != null){
                        System.out.print("New owner: ");
                        String owner = sc.nextLine();
                        card.setOwner(owner);
                        cardService.updateCard(card);
                    }
                    else {
                        System.out.println("Card not found");
                    }
                }

                case 30 -> {
                    System.out.print("Card number to delete: ");
                    String cardNr = sc.nextLine();
                    cardService.deleteCard(cardNr);
                }

                case 0 -> {
                    System.out.println("Exiting app.");
                    AuditService.getInstance().close();
                    running = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }
        sc.close();
    }

    private static Account findAccountByIban(AccountService accountService, String iban){
        for (Account acc : accountService.getAllAccounts()){
            if (acc.getIban().equalsIgnoreCase(iban)) return acc;
        }
        return null;
    }

    private static Client findClientById(ClientService cs, String id){
        for (Client client : cs.getClients()){
            if (client.getId().equalsIgnoreCase(id)) return client;
        }
        return null;
    }
}
