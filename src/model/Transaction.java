package model;

import util.TransactionIdGenerator;

import java.time.LocalDate;

public class Transaction implements Comparable<Transaction> {
    private final String id;
    private String description;
    private double amount;
    private TransactionType type;
    private final LocalDate date;
    private String accountId;

    public Transaction(String description, double amount, TransactionType type, LocalDate date, String accountId){
        this.id = TransactionIdGenerator.generateTransactionId();
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.accountId = accountId;
    }

    public Transaction(String id, String description, double amount, TransactionType type, LocalDate date, String accountId){
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.accountId = accountId;
    }


    public String getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public double getAmount(){
        return amount;
    }

    public TransactionType getType(){
        return type;
    }

    public LocalDate getDate(){
        return date;
    }

    public String getAccountId(){
        return accountId;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public int compareTo(Transaction anotherOne){
        int dateComp = this.date.compareTo(anotherOne.date);
        if (dateComp != 0){
            return dateComp;
        }
        return this.id.compareTo(anotherOne.id);
    }

    @Override
    public String toString(){
        return "|" + date + "| " + " " + type + ": " + amount + " " + description + " id: " + id;
    }
}
