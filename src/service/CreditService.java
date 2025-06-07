package service;

import model.*;

import java.time.LocalDate;
import java.util.List;

public class CreditService {
    // Repay credit for the credit card of an account
    public void repayCreditForCard(Account account, double amount, String description, LocalDate date){
        boolean repaid = false;
        CardService cardService = new CardService();
        List<Card> cards = cardService.getCardsForAccount(account.getId());
        TransactionService transactionService = new TransactionService();

        for (Card card : cards){
            if (card instanceof CreditCard){
                ((CreditCard) card).repayCredit(amount);
                transactionService.performTransaction(account, amount, description, TransactionType.DEPOSIT, date);

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
