package service;

import model.*;

import java.time.LocalDate;

public class AccountService {

    // 1. Transfer from an account to another
    public void transferBetweenAccounts(Account sender, Account receiver, double amount, String description, LocalDate date){
        try {
            sender.withdraw(amount);
            receiver.deposit(amount);

            Transaction senderT = new Transaction(description, amount, TransactionType.TRANSFER, date);
            Transaction receiverT = new Transaction(description, amount, TransactionType.TRANSFER, date);

            sender.addTransaction(senderT);
            receiver.addTransaction(receiverT);

            Notification senderN = new Notification("Transferred " + amount + " to " + receiver.getIban(), date);
            Notification receiverN = new Notification("Received " + amount + " fom " + sender.getIban(), date);

            sender.addNotification(senderN);
            receiver.addNotification(receiverN);
            System.out.println("Transfer successful");
        }
        catch (IllegalArgumentException exc){
            System.out.println("Transfer failed: " + exc.getMessage());
        }
    }

    // 2. Check for inactive accounts (with no transactions in the last month)
    public void checkForInactiveAccounts(Client client){
        LocalDate aMonthAgo = LocalDate.now().minusMonths(1);
        boolean inactive = false;
        System.out.println("Inactive accounts: ");
        for (Account account : client.getAccounts()){
            boolean active = false;
            for (Transaction transaction : account.getTransactions()){
                if (transaction.getDate().isAfter(aMonthAgo)){
                    active = true;
                    break;
                }
            }

            if (!active){
                inactive = true;
                System.out.println(account);
            }
        }
        if (!inactive){
            System.out.println("No inactive accounts found.");
        }
    }

    // 3. Generate Account Statement
    public AccountStatement generateAccountStatement(Account account, LocalDate startDate, LocalDate endDate){
        AccountStatement statement = new AccountStatement(account, startDate, endDate);
        statement.printStatement();
        return statement;
    }

    // 4. Search for transactions made after a certain date
    public void searchForTransactionsAfterDate(Account account, LocalDate date){
        boolean found = false;
        System.out.println("Transactions made after " + date);
        for (Transaction transaction : account.getTransactions()){
            if (transaction.getDate().isAfter(date)){
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found){
            System.out.println("No transactions found after " + date);
        }
    }

    // 5. Show transactions made with the debit card
    public void showDebitPayments(Account account){
        boolean found = false;
        System.out.println("Transactions made with the debit card: ");
        for (Transaction transaction : account.getTransactions()){
            if (transaction.getType() == TransactionType.CARD_PAYMENT){
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found){
            System.out.println("No transactions made with the debit card");
        }
    }

    // 6. Increase overdraft limit for a current account
    public void increaseOverdraftLimitForAccount(Account account, double amount){
        if (account instanceof CurrentAccount){
            ((CurrentAccount) account).increaseOverdraftLimit(amount);
            System.out.println("Overdraft limit increased by " + amount + " for account " + account.getIban());
        }
        else {
            System.out.println("The selected account is not a current account. Cannot increase overdraft.");
        }
    }
}
