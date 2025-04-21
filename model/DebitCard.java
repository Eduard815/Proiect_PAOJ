package model;

import java.time.LocalDate;

public class DebitCard extends Card{
    public DebitCard(LocalDate expirationDate, int ccv, Account account, String owner, boolean active){
        super(expirationDate, ccv, account, owner, active);
    }

    public void makePayment(double amount, String description, TransactionType type, LocalDate date){
        if (!isActive()){
            throw new IllegalStateException("The card is inactive!");
        }

        Account account = getAccount();
        account.withdraw(amount);

        Transaction transaction = new Transaction(description, amount, type, date);
        account.addTransaction(transaction);

        String notMessage = "Payment of " + amount + " made with the debit card. Description: " + description;
        Notification notification = new Notification(notMessage, date);
        account.addNotification(notification);
    }

    @Override 
    public String getCardType(){
        return "Debit Card";
    }
}
