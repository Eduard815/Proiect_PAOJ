package service;

import model.*;

import java.time.LocalDate;

public class CreditService {
    // Repay credit for the credit card of an account
    public void repayCreditForCard(Account account, double amount, String description, LocalDate date){
        boolean repaid = false;
        for (Card card : account.getCards()){
            if (card instanceof CreditCard){
                ((CreditCard) card).repayCredit(amount);
                Transaction transaction = new Transaction(description, amount, TransactionType.DEPOSIT, date);
                account.addTransaction(transaction);
                String message = "Credit repayment of " + amount + " on card " + card.getCardNumber();
                Notification notification = new Notification(message, date);
                account.addNotification(notification);

                System.out.println("Credit repayment successful");
                repaid = true;
                break;
            }
        }
        if (!repaid){
            System.out.println("No credit card found.");
        }
    }
}
