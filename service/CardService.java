package service;

import model.*;

import java.time.LocalDate;

public class CardService {
    // 1. Generate credit card for an account
    public void generateCreditCard(Account account, LocalDate expirationDate, int ccv, String owner, double creditLimit, double interestRate){
        CreditCard creditCard = new CreditCard(expirationDate, ccv, account, owner, true, creditLimit, interestRate);
        account.addCard(creditCard);
        System.out.println("Credit card generated for " + owner + ", Card number: " + creditCard.getCardNumber());
    }

    // 2. Generate debit card for an account
    public void generateDebitCard(Account account, LocalDate expirationDate, int ccv, String owner){
        DebitCard debitCard = new DebitCard(expirationDate, ccv, account, owner, true);
        account.addCard(debitCard);
        System.out.println("Debit card generated for " + owner + ". Card number: " + debitCard.getCardNumber());
    }

    // 3. Deactivate and reactivate a card
    public void deactivateReactivateCard(Account account, String cardNumber, boolean activate){
        boolean found = false;
        for (Card card : account.getCards()){
            if (card.getCardNumber().equals(cardNumber)){
                if (activate){
                    card.activateCard();
                    System.out.println("Card " + cardNumber + " activated");
                }
                else {
                    card.deactivateCard();
                    System.out.println("Card " + cardNumber + " deactivated");
                }
                found = true;
                break;
            }
        }
        if (!found){
            System.out.println("Card " + cardNumber + " not found.");
        }
    }
}
