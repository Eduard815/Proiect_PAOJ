package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private final Connection connection;
    public AccountDAO(){
        this.connection = DatabaseConnection.getConnection();
    }

    public void createAccount(Account account, String clientId){
        String sql = "INSERT INTO accounts (id, iban, balance, opened_date, type, client_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, account.getId());
            statement.setString(2, account.getIban());
            statement.setDouble(3, account.getBalance());
            statement.setDate(4, Date.valueOf(account.getOpenedDate()));
            statement.setString(5, account instanceof CurrentAccount ? "current" : "savings");
            statement.setString(6, clientId);

            statement.executeUpdate();
            System.out.println("Account created succesfully");
        }
        catch (SQLException e){
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    public List<Account>getAllAccounts(){
        List<Account>accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try(PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                String id = rs.getString("id");
                String iban = rs.getString("iban");
                double balance = rs.getDouble("balance");
                LocalDate openedDate = rs.getDate("opened_date").toLocalDate();
                String type = rs.getString("type");
                String clientId = rs.getString("client_id");

                Account account;
                if ("current".equalsIgnoreCase(type)){
                    double overdraft = rs.getDouble("overdraft_limit");
                    account = new CurrentAccount(String.valueOf(id), iban, balance, openedDate, overdraft, clientId);
                }
                else {
                    double minBalance = rs.getDouble("min_balance");
                    double interestRate = rs.getDouble("interest_rate");
                    boolean isBlocked = rs.getBoolean("is_blocked");
                    account = new SavingsAccount(String.valueOf(id), iban, balance, openedDate, minBalance, interestRate, isBlocked, clientId);
                }
                account.setId(String.valueOf(id));
                account.setIban(iban);
                accounts.add(account);
            }
        }
        catch (SQLException e){
            System.out.println("Error retrieving accounts: " + e.getMessage());
        }
        return accounts;
    }

    public Account getAccountById(String id){
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                String iban = rs.getString("iban");
                double balance = rs.getDouble("balance");
                LocalDate openedDate = rs.getDate("opened_date").toLocalDate();
                String type = rs.getString("type");
                String clientId = rs.getString("client_id");

                Account account;
                if ("current".equalsIgnoreCase(type)){
                    double overdraft = rs.getDouble("overdraft_limit");
                    account = new CurrentAccount(id, iban, balance, openedDate, overdraft, clientId);
                }
                else {
                    double minBalance = rs.getDouble("min_balance");
                    double interestRate = rs.getDouble("interest_rate");
                    boolean isBlocked = rs.getBoolean("is_blocked");
                    account = new SavingsAccount(id, iban, balance, openedDate, minBalance, interestRate, isBlocked, clientId);
                }

                return account;
            }
        }
        catch (SQLException e){
            System.out.println("Error retrieving account: " + e.getMessage());
        }
        return null;
    }


    public void updateAccount(Account account){
        String sql = "UPDATE accounts SET iban = ?, balance = ?, opened_date = ?, type = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, account.getIban());
            statement.setDouble(2, account.getBalance());
            statement.setDate(3, Date.valueOf(account.getOpenedDate()));
            statement.setString(4, account instanceof CurrentAccount ? "current" : "savings");
            statement.setString(5, account.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0){
                System.out.println("Account updated succesfully.");
            }
            else {
                System.out.println("No account found with id " + account.getId());
            }
        }
        catch (SQLException e){
            System.out.println("Error updating account: " + e.getMessage());
        }
    }

    public void deleteAccount(String id){
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, (id));
            int rows = statement.executeUpdate();
            if (rows > 0){
                System.out.println("Account deleted succesfully.");
            }
            else {
                System.out.println("No account found with id " + id);
            }
        }
        catch (SQLException e){
            System.out.println("Error deleting account: " + e.getMessage());
        }
    }
}
