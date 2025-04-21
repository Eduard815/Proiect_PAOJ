package service;

import model.*;

import java.time.LocalDate;

public class TransactionService {
    // 1. Make a transaction
    public void performTransaction(Account account, double amount, String description, TransactionType type, LocalDate date){
        try {
            if (type == TransactionType.DEPOSIT){
                account.deposit(amount);
            }
            else if (type == TransactionType.WITHDRAWAL || type == TransactionType.CARD_PAYMENT){
                account.withdraw(amount);
            }

            Transaction transaction = new Transaction(description, amount, type, date);
            account.addTransaction(transaction);

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
        for (Transaction transaction : account.getTransactions()){
            if (transaction.getDate().equals(date)){
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found){
            System.out.println("No transactions made on " + date);
        }
    }
}
