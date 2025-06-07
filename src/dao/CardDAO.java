package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CardDAO {
    private final Connection connection;
    public CardDAO(){
        this.connection = DatabaseConnection.getConnection();
    }

    public void createCard(Card card, String accountId){
        String sql = "INSERT INTO cards (card_number, expiration_date, ccv, owner, active, type, account_id, credit_limit, interest_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement  = connection.prepareStatement(sql)){
            statement.setString(1, card.getCardNumber());
            statement.setDate(2, Date.valueOf(card.getExpirationDate()));
            statement.setInt(3, card.getCCV());
            statement.setString(4, card.getOwner());
            statement.setBoolean(5, card.isActive());
            statement.setString(6, card instanceof CreditCard ? "credit" : "debit");
            statement.setString(7, accountId);

            if (card instanceof CreditCard creditCard){
                statement.setDouble(8, creditCard.getCreditLimit());
                statement.setDouble(9, creditCard.getInterestRate());
            }
            else {
                statement.setNull(8, Types.DOUBLE);
                statement.setNull(9, Types.DOUBLE);
            }
            statement.executeUpdate();
            System.out.println("Card created successfully.");
        }
        catch (SQLException e){
            System.out.println("Error creating card: " + e.getMessage());
        }
    }

    public List<Card>getAllCards(){
        List<Card>cards = new ArrayList<>();
        String sql = "SELECT * FROM cards";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                String cardNumber = rs.getString("card_number");
                LocalDate expirationDate = rs.getDate("expiration_date").toLocalDate();
                int ccv = rs.getInt("ccv");
                String owner = rs.getString("owner");
                boolean active = rs.getBoolean("active");
                String type = rs.getString("type");

                String accountId = rs.getString("account_id");
                AccountDAO accountDAO = new AccountDAO();
                Account account = accountDAO.getAccountById(accountId);

                Card card;
                if ("credit".equalsIgnoreCase(type)){
                    double creditLimit = rs.getDouble("credit_limit");
                    double interestRate = rs.getDouble("interest_rate");

                    card = new CreditCard(cardNumber, expirationDate, ccv, account, owner, active, creditLimit, interestRate);
                } else {
                    card = new DebitCard(cardNumber, expirationDate, ccv, account, owner, active);
                }

                cards.add(card);

            }
        }
        catch (SQLException e){
            System.out.println("Error retrieving cards: " + e.getMessage());
        }
        return cards;
    }

    public Card getCardByNumber(String cardNumber){
        String sql = "SELECT * FROM cards WHERE card_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, cardNumber);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                String dbCardNumber = rs.getString("card_number");
                LocalDate expirationDate = rs.getDate("expiration_date").toLocalDate();
                int ccv = rs.getInt("ccv");
                String owner = rs.getString("owner");
                boolean active = rs.getBoolean("active");
                String type = rs.getString("type");

                String accountId = rs.getString("account_id");
                AccountDAO accountDAO = new AccountDAO();
                Account account = accountDAO.getAccountById(accountId);

                Card card;
                if ("credit".equalsIgnoreCase(type)){
                    double creditLimit = rs.getDouble("credit_limit");
                    double interestRate = rs.getDouble("interest_rate");

                    card = new CreditCard(dbCardNumber, expirationDate, ccv, account, owner, active, creditLimit, interestRate);
                }
                else {
                    card = new DebitCard(dbCardNumber, expirationDate, ccv, account, owner, active);
                }

                return card;
            }
        }
        catch (SQLException e){
            System.out.println("Error retrieving card: " + e.getMessage());
        }
        return null;
    }


    public void updateCard(Card card, String cardNumber){
        String sql = "UPDATE cards SET expiration_date = ?, ccv = ?, owner = ?, active = ?, type = ?, credit_limit = ?, interest_rate = ? WHERE card_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1, Date.valueOf(card.getExpirationDate()));
            statement.setInt(2, card.getCCV());
            statement.setString(3, card.getOwner());
            statement.setBoolean(4, card.isActive());
            statement.setString(5, card instanceof CreditCard ? "credit" : "debit");

            if (card instanceof CreditCard creditCard){
                statement.setDouble(6, creditCard.getCreditLimit());
                statement.setDouble(7, creditCard.getInterestRate());
            }
            else {
                statement.setNull(6, Types.DOUBLE);
                statement.setNull(7, Types.DOUBLE);
            }

            statement.setString(8, cardNumber);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0){
                System.out.println("Card updated successfully.");
            }
            else {
                System.out.println("No card found with number " + cardNumber);
            }
        }
        catch (SQLException e){
            System.out.println("Error updating card: " + e.getMessage());
        }
    }

    public void deleteCard(String cardNumber){
        String sql = "DELETE FROM cards WHERE card_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, cardNumber);
            int rows = statement.executeUpdate();

            if (rows > 0){
                System.out.println("Card deleted successfully.");
            }
            else {
                System.out.println("No card found with number " + cardNumber);
            }
        }
        catch (SQLException e){
            System.out.println("Error deleting card: " + e.getMessage());
        }
    }
}
