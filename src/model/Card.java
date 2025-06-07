package model;

import util.CardNumberGenerator;

import java.time.LocalDate;

public abstract class Card {
    private String cardNumber;
    private LocalDate expirationDate;
    private int ccv;
    private Account account;
    private String owner;
    private boolean active;

    public Card(LocalDate expirationDate, int ccv, Account account, String owner, boolean active){
        this.cardNumber = CardNumberGenerator.generateCardNumber();
        this.ccv = ccv;
        this.expirationDate = expirationDate;
        this.account = account;
        this.owner = owner;
        this.active = active;
    }

    public Card(String cardNumber, LocalDate expirationDate, int ccv, Account account, String owner, boolean active){
        this.cardNumber = cardNumber;
        this.ccv = ccv;
        this.expirationDate = expirationDate;
        this.account = account;
        this.owner = owner;
        this.active = active;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public LocalDate getExpirationDate(){
        return expirationDate;
    }

    public int getCCV(){
        return ccv;
    }

    public Account getAccount(){
        return account;
    }

    public String getOwner(){
        return owner;
    }

    public boolean isActive(){
        return active;
    }

    public void activateCard(){
        active = true;
    }

    public void deactivateCard(){
        active = false;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public abstract String getCardType();
}
