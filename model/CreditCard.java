package model;

import java.time.LocalDate;

public class CreditCard extends Card{
    private double creditLimit;
    private double availableCredit;
    private double interestRate;

    public CreditCard(LocalDate expirationDate, int ccv, Account account, String owner, boolean active, double creditLimit,  double interestRate){
        super(expirationDate, ccv, account, owner, active);
        this.creditLimit = creditLimit;
        this.availableCredit = creditLimit;
        this.interestRate = interestRate;
    }

    public void makePayment(double amount, String description, TransactionType type, LocalDate date){
        if (!isActive()){
            throw new IllegalStateException("Card is inactive!");
        }
        if (availableCredit < amount){
            throw new IllegalArgumentException("Insufficient available credit!");
        }

        Account account = getAccount();

        availableCredit -= amount;
        Transaction transaction = new Transaction(description, amount, type, date);
        account.addTransaction(transaction);

        String notMessage = "Payment of " + amount + " made with the credit card. Description: " + description;
        Notification notification = new Notification(notMessage, date);
        account.addNotification(notification);
    }

    public void applyInterest(LocalDate date){
        double usedCredit = creditLimit - availableCredit;

        if (usedCredit <= 0){
            return;
        }

        double monthlyInterestRate = interestRate / 12;
        double interest = usedCredit * monthlyInterestRate;

        availableCredit -= interest;

        if (availableCredit < 0){
            availableCredit = 0;
        }
        String message = "Monthly interest of " + interest + " RON applied to credit.";
        Notification notification = new Notification(message, date);
        getAccount().addNotification(notification);

        Transaction transaction = new Transaction("Interest on credit", interest, TransactionType.INTEREST, date);
        getAccount().addTransaction(transaction);
    }

    public void repayCredit(double amount){
        availableCredit = Math.min(availableCredit + amount, creditLimit);
    }

    public double getCreditLimit(){
        return creditLimit;
    }

    public double getAvailableCredit(){
        return availableCredit;
    }

    public double getInterestRate(){
        return interestRate;
    }

    @Override
    public String getCardType(){
        return "Credit Card";
    }
}
