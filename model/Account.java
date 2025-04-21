package model;

import util.AccountIdGenerator;
import util.IbanGenerator;


import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

public abstract class Account {
    private String id;
    private String iban;
    protected double balance;
    protected LocalDate openedDate;

    private Set<Transaction> transactions;
    private List<Notification> notifications;
    private List<Card> cards;

    public Account(double balance, LocalDate openedDate){
        id = AccountIdGenerator.generateId();
        iban = IbanGenerator.generateIban();
        this.balance = balance;
        this.openedDate = openedDate;


        transactions = new TreeSet<Transaction>();
        notifications = new ArrayList<Notification>();
        cards = new ArrayList<Card>();
    }

    public String getId(){
        return id;
    }
    public String getIban(){
        return iban;
    }
    public double getBalance(){
        return balance;
    }
    public Set<Transaction> getTransactions(){
        return transactions;
    }
    public List<Notification> getNotifications(){
        return notifications;
    }
    public List<Card> getCards(){
        return cards;
    }

    public void setOpenedDate(LocalDate openedDate){
        this.openedDate = openedDate;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public void deposit(double amount){
        balance += amount;
    }

    public LocalDate getOpenedDate(){
        return openedDate;
    }

    public void withdraw(double amount){
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient funds!");
        }
        balance -= amount;
    }

    public void addTransaction(Transaction t){
        transactions.add(t);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public void addNotification(Notification n){
        notifications.add(n);
    }

    public void showAllNotifications(){
        for (Notification n : notifications){
            System.out.println(n);
            n.markAsRead();
        } 
    }

    public abstract String getAccountType();

    @Override
    public String toString(){
        return "Account type " + getAccountType() + " Iban: " + iban + " Balance: " + balance + " Opened on: " + openedDate;
    }
}
