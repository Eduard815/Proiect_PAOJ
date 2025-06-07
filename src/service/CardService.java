package service;

import model.*;
import dao.*;

import java.time.LocalDate;
import java.util.List;

public class CardService {
    private CardDAO cardDAO;
    public CardService(){
        cardDAO = new CardDAO();
    }

    public List<Card> getCardsForAccount(String accountId){
    return cardDAO.getAllCards().stream()
                .filter(c -> c.getAccount().getId().equals(accountId))
                .toList();
    }


    // 1. Generate credit card for an account
    public void generateCreditCard(Account account, LocalDate expirationDate, int ccv, String owner, double creditLimit, double interestRate){
        CreditCard creditCard = new CreditCard(expirationDate, ccv, account, owner, true, creditLimit, interestRate);
        account.addCard(creditCard);
        cardDAO.createCard(creditCard, account.getId());  /// C
        System.out.println("Credit card generated for " + owner + ", Card number: " + creditCard.getCardNumber());
    }

    // 2. Generate debit card for an account
    public void generateDebitCard(Account account, LocalDate expirationDate, int ccv, String owner){
        DebitCard debitCard = new DebitCard(expirationDate, ccv, account, owner, true);
        account.addCard(debitCard);
        cardDAO.createCard(debitCard, account.getId());   /// C
        System.out.println("Debit card generated for " + owner + ". Card number: " + debitCard.getCardNumber());
    }

    // 3. Deactivate and reactivate a card
    public void deactivateReactivateCard(Account account, String cardNumber, boolean activate){
        boolean found = false;
        List<Card>cards = getCardsForAccount(account.getId());
        for (Card card : cards){
            if (card.getCardNumber().equals(cardNumber)){
                if (activate){
                    card.activateCard();
                    System.out.println("Card " + cardNumber + " activated");
                }
                else {
                    card.deactivateCard();
                    System.out.println("Card " + cardNumber + " deactivated");
                }
                cardDAO.updateCard(card, cardNumber); /// U
                found = true;
                break;
            }
        }
        if (!found){
            System.out.println("Card " + cardNumber + " not found.");
        }
    }

    /// R
    public void showAllCards(){
        var cards = cardDAO.getAllCards();
        if (cards.isEmpty()){
            System.out.println("No cards found.");
        }
        else {
            for (Card card : cards){
                System.out.println(card.getCardType() + " " + card.getCardNumber() + " Owner: " + card.getOwner() + " Active: " + card.isActive());
            }
        }
    }

    public Card getCardByNumber(String cardNumber){
        return cardDAO.getCardByNumber(cardNumber);
    }

    /// U
    public void updateCard(Card card){
        cardDAO.updateCard(card, card.getCardNumber());
        System.out.println("Card updated in database");
    }

    /// D
    public void deleteCard(String cardNumber){
        cardDAO.deleteCard(cardNumber);
        System.out.println("Card deleted from database");
    }
}
