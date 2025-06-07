package service;

import model.*;
import dao.*;

import java.time.LocalDate;
import java.util.List;

public class TransactionService {
    private TransactionDAO transactionDAO;
    
    public TransactionService(){
        transactionDAO = new TransactionDAO();
    }

    public List<Transaction> getTransactionsForAccount(String accountId){
        return transactionDAO.getAllTransactions().stream()
                .filter(t -> t.getAccountId() != null && t.getAccountId().equals(accountId))
                .toList();
    }


    // 1. Make a transaction
    public void performTransaction(Account account, double amount, String description, TransactionType type, LocalDate date){
        try {
            if (type == TransactionType.DEPOSIT){
                account.deposit(amount);
            }
            else if (type == TransactionType.WITHDRAWAL || type == TransactionType.CARD_PAYMENT){
                account.withdraw(amount);
            }

            Transaction transaction = new Transaction(description, amount, type, date, account.getId());
            account.addTransaction(transaction);

            transactionDAO.createTransaction(transaction, account.getId()); /// C

            String message = "Transaction " + type + " of " + amount + ". Description: " + description;
            Notification notification = new Notification(message, date);
            account.addNotification(notification);

            System.out.println("Transaction successful: " + type + " of " + amount);
        }
        catch (IllegalArgumentException exc){
            System.out.println("Transaction failed: " + exc.getMessage());
        }
    }

    // 2. Search for transactions made on a certain date
    public void findTransactionsMadeOnDate(Account account, LocalDate date){
        boolean found = false;
        System.out.println("Transactions on " + date + ":");
        List<Transaction> transactions = getTransactionsForAccount(account.getId());
        for (Transaction transaction : transactions){
            if (transaction.getDate().equals(date)){
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found){
            System.out.println("No transactions made on " + date);
        }
    }


    /// R
    public void showAllTransactions(){
        var transactions = transactionDAO.getAllTransactions();
        if (transactions.isEmpty()){
            System.out.println("No transactions found.");
        }
        else {
            for (Transaction transaction : transactions){
                System.out.println(transaction);
            }
        }
    }

    public Transaction getTransactionById(String id){
        return transactionDAO.getTransactionById(id);
    }


    /// U
    public void updateTransaction(Transaction transaction){
        transactionDAO.updateTransaction(transaction, transaction.getId());
        System.out.println("Transaction updated in database.");
    }

    /// D
    public void deleteTransaction(String id){
        transactionDAO.deleteTransaction(id);
        System.out.println("Transaction deleted from database.");
    }
}
