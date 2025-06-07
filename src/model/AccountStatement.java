package model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
// import java.util.Set;

import service.TransactionService;

public class AccountStatement {
    private Account account;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate generationDate;
    private List<Transaction> transactions;
    private TransactionService transactionService = new TransactionService();


    public AccountStatement(Account account, LocalDate startDate, LocalDate endDate){
        this.account = account;
        this.startDate = startDate;
        this.endDate = endDate;
        this.generationDate = LocalDate.now();
        this.transactions = new ArrayList<>();

        generateStatement();
    }

    public void generateStatement(){
        List<Transaction> allTransactions = transactionService.getTransactionsForAccount(account.getId());
        for (Transaction transaction : allTransactions){
            if (!transaction.getDate().isBefore(startDate) && !transaction.getDate().isAfter(endDate)){
                transactions.add(transaction);
            }
        }
    }


    public void printStatement(){
        System.out.println("---------STATEMENT---------");
        System.out.println("IBAN: " + account.getIban());
        System.out.println("Period: " + startDate + " -> " + endDate);
        System.out.println("Generated at: " + generationDate);
        System.out.println("Transactions made: ");
        if (transactions.isEmpty()){
            System.out.println("No transactions were made in this period.");
        }
        else {
            for (Transaction transaction : transactions){
                System.out.println(transaction);
            }
        }
        System.out.println("------------------------------");
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public LocalDate getGenerationDate(){
        return generationDate;
    }
}
