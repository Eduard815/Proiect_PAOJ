package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private final Connection connection;
    public TransactionDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void createTransaction(Transaction transaction, String accountId){
        String sql = "INSERT INTO transactions (id, description, amount, type, date, account_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, transaction.getId());
            statement.setString(2, transaction.getDescription());
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getType().name());
            statement.setDate(5, Date.valueOf(transaction.getDate()));
            statement.setString(6, accountId);

            statement.executeUpdate();
            System.out.println("Transaction created successfully.");
        }
        catch (SQLException e){
            System.out.println("Error creating transaction: " + e.getMessage());
        }
    }

    public List<Transaction> getAllTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                String id = rs.getString("id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));
                LocalDate date = rs.getDate("date").toLocalDate();
                String accountId = rs.getString("account_id");

                Transaction transaction = new Transaction(id, description, amount, type, date, accountId);
                transactions.add(transaction);
            }
        }
        catch (SQLException e){
            System.out.println("Error retrieving transactions: " + e.getMessage());
        }
        return transactions;
    }


    public Transaction getTransactionById(String id){
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));
                LocalDate date = rs.getDate("date").toLocalDate();
                String accountId = rs.getString("account_id");

                Transaction transaction = new Transaction(id, description, amount, type, date, accountId);
                return transaction;
            }
        }
        catch (SQLException e){
            System.out.println("Error retrieving transaction: " + e.getMessage());
        }
        return null;
    }


    public void updateTransaction(Transaction transaction, String transactionId){
        String sql = "UPDATE transactions SET description = ?, amount = ?, type = ?, date = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, transaction.getDescription());
            statement.setDouble(2, transaction.getAmount());
            statement.setString(3, transaction.getType().name());
            statement.setDate(4, Date.valueOf(transaction.getDate()));
            statement.setString(5, transactionId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0){
                System.out.println("Transaction updated successfully.");
            }
            else {
                System.out.println("No transaction found with id " + transactionId);
            }
        }
        catch (SQLException e){
            System.out.println("Error updating transaction: " + e.getMessage());
        }
    }

    public void deleteTransaction(String id){
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, id);
            int rows = statement.executeUpdate();

            if (rows > 0){
                System.out.println("Transaction deleted successfully.");
            }
            else {
                System.out.println("No transaction found with id " + id);
            }
        }
        catch (SQLException e){
            System.out.println("Error deleting transaction: " + e.getMessage());
        }
    }
}
