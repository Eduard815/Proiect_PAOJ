package model;

import java.time.LocalDate;

public class CurrentAccount extends Account{
    private double overdraftLimit;
    private boolean overdraftEnabled;

    public CurrentAccount(double balance, LocalDate openedDate, double overdraftLimit){
        super(balance, openedDate);
        this.overdraftLimit = overdraftLimit;
        overdraftEnabled = false;
    }

    @Override
    public void withdraw(double amount){
        if (getBalance() + overdraftLimit < amount){
            throw new IllegalArgumentException("Insufficient funds!");
        }
        if (getBalance() >= amount){
            super.withdraw(amount);
        }
        else if (getBalance() + overdraftLimit >= amount){
            overdraftLimit -= (amount - getBalance());
            setBalance(0);
            enableOverdraft();
        }
    }

    public double getAvailableFunds(){
        return overdraftLimit + getBalance();
    }

    public double getOverdraftLimit(){
        return overdraftLimit;
    }

    public boolean isOverdraftEnabled(){
        return overdraftEnabled;
    }

    public void enableOverdraft(){
        overdraftEnabled = true;
    }

    public void disableOverdraft(){
        overdraftEnabled = false;
    }

    public void increaseOverdraftLimit(double amount){
        overdraftLimit += amount;
    }

    public void resetOverdraft(double value){
        overdraftLimit += value;
        if (overdraftLimit > 0){
            overdraftEnabled = false;
        }
    }

    @Override
    public String getAccountType(){
        return "Current";
    }
}
