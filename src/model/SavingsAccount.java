package model;

import java.time.LocalDate;

public class SavingsAccount extends Account{
    private final double minimumBalance;
    private double interestRate;     // Annual interest rate applied every month
    private boolean blocked;

    public SavingsAccount(double balance, LocalDate openedDate, double minimumBalance, double interestRate, boolean blocked){
        super(balance, openedDate);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.blocked = blocked;
    }

    /// Overload
    public SavingsAccount(String id, String iban, double balance, LocalDate openedDate, double minBalance, double interestRate, boolean isBlocked, String clientId){
        super(balance, openedDate);
        this.setId(id);
        this.setIban(iban);
        this.minimumBalance = minBalance;
        this.interestRate = interestRate;
        this.blocked = isBlocked;
        this.setClientId(clientId);
    }


    @Override
    public void withdraw(double amount){
        if (isBlocked()){
            throw new IllegalArgumentException("The account is currently blocked!");
        }
        if (getBalance() < minimumBalance){
            throw new IllegalArgumentException("Cannot withdraw. Balance below the required minimum!");
        }
        LocalDate currentTime = LocalDate.now();
        int monthsPassed = java.time.Period.between(getOpenedDate(), currentTime).getMonths() + java.time.Period.between(getOpenedDate(), currentTime).getYears() * 12;
        for (int i = 0; i < monthsPassed; i++){
            applyMonthlyInterest();
        }
        setBalance(getBalance() - amount);
    }

    public void applyMonthlyInterest(){
        double monthlyInterest = interestRate / 12;
        double interest = getBalance() * monthlyInterest;
        deposit(interest);
    }

    public void blockAccount(){
        blocked = true;
    }

    public void unblockAccount(){
        blocked = false;
    }

    public double getMinimumBalance(){
        return minimumBalance;
    }

    public double getInterestRate(){
        return interestRate;
    }

    public boolean isBlocked(){
        return blocked;
    }

    @Override
    public String getAccountType(){
        return "Savings Account";
    }
}
