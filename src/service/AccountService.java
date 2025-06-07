package service;

import model.*;
import dao.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AccountService {

    private AccountDAO accountDAO;
    private TransactionService transactionService;
    private NotificationService notificationService;

    public AccountService(){
        accountDAO = new AccountDAO();
        transactionService = new TransactionService();
        notificationService = new NotificationService();
    }

    public List<Account> getAccountsForClient(String clientId){
        return accountDAO.getAllAccounts().stream()
                .filter(acc -> acc.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    public void createAccount(Account account, String clientId){
        accountDAO.createAccount(account, clientId);
        System.out.println("Account created in database.");
    }

    public void showAllAccounts(){
        var accounts = accountDAO.getAllAccounts();
        if (accounts.isEmpty()){
            System.out.println("No accounts found.");
        }
        else {
            for (Account account : accounts){
                System.out.println(account);
            }
        }
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }


    public void updateAccount(Account account){
        accountDAO.updateAccount(account);
        System.out.println("Account updated in the database.");
    }

    public void deleteAccount(String id){
        accountDAO.deleteAccount(id);
        System.out.println("Account deleted from database.");
    }

    public Account getAccountById(String id){
        return accountDAO.getAccountById(id);
    }

    public void checkForInactiveAccounts(String clientId){
        List<Account> accounts = getAccountsForClient(clientId);
        LocalDate aMonthAgo = LocalDate.now().minusMonths(1);
        boolean inactive = false;

        System.out.println("Inactive accounts: ");
        for (Account account : accounts){
            List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
            boolean active = transactions.stream().anyMatch(t -> t.getDate().isAfter(aMonthAgo));

            if (!active){
                inactive = true;
                System.out.println(account);
            }
        }

        if (!inactive){
            System.out.println("No inactive accounts found.");
        }
    }

    public AccountStatement generateAccountStatement(Account account, LocalDate startDate, LocalDate endDate){
        AccountStatement statement = new AccountStatement(account, startDate, endDate);
        statement.printStatement();
        return statement;
    }

    public void searchForTransactionsAfterDate(Account account, LocalDate date){
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        boolean found = false;
        System.out.println("Transactions made after " + date);
        for (Transaction transaction : transactions){
            if (transaction.getDate().isAfter(date)){
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found){
            System.out.println("No transactions found after " + date);
        }
    }

    public void showDebitPayments(Account account){
        List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getId());
        boolean found = false;
        System.out.println("Transactions made with the debit card: ");
        for (Transaction transaction : transactions){
            if (transaction.getType() == TransactionType.CARD_PAYMENT){
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found){
            System.out.println("No transactions made with the debit card");
        }
    }

    public void increaseOverdraftLimitForAccount(Account account, double amount){
        if (account instanceof CurrentAccount){
            ((CurrentAccount) account).increaseOverdraftLimit(amount);
            accountDAO.updateAccount(account);
            System.out.println("Overdraft limit increased by " + amount + " for account " + account.getIban());
        }
        else {
            System.out.println("The selected account is not a current account. Cannot increase overdraft.");
        }
    }

    public void transferBetweenAccounts(Account sender, Account receiver, double amount, String description, LocalDate date){
        try {
            sender.withdraw(amount);
            receiver.deposit(amount);

            accountDAO.updateAccount(sender);
            accountDAO.updateAccount(receiver);

            transactionService.performTransaction(sender, amount, description, TransactionType.TRANSFER, date);
            transactionService.performTransaction(receiver, amount, description, TransactionType.TRANSFER, date);

            System.out.println("Transfer successful");
        }
        catch (IllegalArgumentException exc){
            System.out.println("Transfer failed: " + exc.getMessage());
        }
    }
}
