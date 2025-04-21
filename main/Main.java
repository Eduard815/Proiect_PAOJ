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
                }

                case 3 -> clientService.showAllClients();

                case 4 -> {
                    System.out.print("Client ID: ");
                    clientService.showAllAccountsForAClient(sc.nextLine());
                }

                case 5 -> {
                    System.out.print("Client ID: ");
                    clientService.showAccountWithMaxBalance(sc.nextLine());
                }

                case 6 -> {
                    System.out.print("Client ID: ");
                    clientService.showActiveInactiveCards(sc.nextLine());
                }

                case 7 -> {
                    System.out.print("Client ID: ");
                    String id = sc.nextLine();
                    System.out.print("Notification message: ");
                    String msg = sc.nextLine();
                    clientService.sendNotificationToClient(id, msg, LocalDate.now());
                }

                case 8 -> {
                    System.out.print("Client ID: ");
                    clientService.showNotificationsForClient(sc.nextLine());
                }

                case 9 -> {
                    System.out.print("Sender IBAN: ");
                    Account sender = findAccountByIban(clientService, sc.nextLine());
                    System.out.print("Receiver IBAN: ");
                    Account receiver = findAccountByIban(clientService, sc.nextLine());

                    if (sender == null || receiver == null){
                        System.out.println("Sender or receiver account not found.");
                        break;
                    }

                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(sc.nextLine());
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    accountService.transferBetweenAccounts(sender, receiver, amount, desc, LocalDate.now());
                }

                case 10 -> {
                    System.out.print("Client ID: ");
                    Client client = findClientById(clientService, sc.nextLine());
                    if (client == null){
                        System.out.println("Client not found.");
                    }
                    else {
                        accountService.checkForInactiveAccounts(client);
                    }
                }

                case 11 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
                    if (acc == null){
                        System.out.println("Account not found.");
                        break;
                    }
                    System.out.print("Start date (yyyy-mm-dd): ");
                    LocalDate start = LocalDate.parse(sc.nextLine());
                    System.out.print("End date (yyyy-mm-dd): ");
                    LocalDate end = LocalDate.parse(sc.nextLine());
                    accountService.generateAccountStatement(acc, start, end);
                }

                case 12 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
                    if (acc == null){
                        System.out.println("Account not found.");
                        break;
                    }
                    System.out.print("Date (yyyy-mm-dd): ");
                    accountService.searchForTransactionsAfterDate(acc, LocalDate.parse(sc.nextLine()));
                }

                case 13 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
                    if (acc != null) accountService.showDebitPayments(acc);
                    else System.out.println("Account not found.");
                }

                case 14 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
                    if (acc != null){
                        System.out.print("Amount to increase: ");
                        double amount = Double.parseDouble(sc.nextLine());
                        accountService.increaseOverdraftLimitForAccount(acc, amount);
                    }
                    else System.out.println("Account not found.");
                }

                case 15 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
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
                }

                case 16 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
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
                }

                case 17 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
                    if (acc != null){
                        System.out.print("Card number: ");
                        String cardNr = sc.nextLine();
                        System.out.print("Activate? (enter true). Deactivate? (enter false) ");
                        boolean activate = Boolean.parseBoolean(sc.nextLine());
                        cardService.deactivateReactivateCard(acc, cardNr, activate);
                    }
                    else System.out.println("Account not found.");
                }

                case 18 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
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
                }

                case 19 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
                    if (acc == null){
                        System.out.println("Account not found.");
                        break;
                    }
                    System.out.print("Date (yyyy-mm-dd): ");
                    transactionService.findTransactionsMadeOnDate(acc, LocalDate.parse(sc.nextLine()));
                }

                case 20 -> {
                    System.out.print("Account IBAN: ");
                    Account acc = findAccountByIban(clientService, sc.nextLine());
                    if (acc != null){
                        System.out.print("Repayment amount: ");
                        double amount = Double.parseDouble(sc.nextLine());
                        System.out.print("Description: ");
                        String desc = sc.nextLine();
                        creditService.repayCreditForCard(acc, amount, desc, LocalDate.now());
                    }
                    else System.out.println("Account not found.");
                }

                case 0 -> {
                    System.out.println("Exiting app.");
                    running = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }
        sc.close();
    }

    private static Account findAccountByIban(ClientService cs, String iban){
        for (Client client : cs.getClients()){
            for (Account acc : client.getAccounts()){
                if (acc.getIban().equalsIgnoreCase(iban)) return acc;
            }
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
