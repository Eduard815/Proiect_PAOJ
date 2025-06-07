package dao;

import model.Client;
import model.Address;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private final Connection connection;

    public ClientDAO(){
        this.connection = DatabaseConnection.getConnection();
    }

    public void createClient(Client client){
        String sql = "INSERT INTO clients (id, name, city, street, number, postal_code) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, client.getId());
            statement.setString(2, client.getName());
            statement.setString(3, client.getAddress().getCity());
            statement.setString(4, client.getAddress().getStreet());
            statement.setInt(5, client.getAddress().getNumber());
            statement.setString(6, client.getAddress().getPostalCode());
            statement.executeUpdate();

            /*
            try (ResultSet generatedKeys = statement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    int id = generatedKeys.getInt(1);
                    client.setId(String.valueOf(id));
                }
            }
            */
            System.out.println("Client inserted succesfully.");
        }
        catch (SQLException e){
            System.out.println("Error inserting client: " + e.getMessage());
        }
    }

    public List<Client>getAllClients(){
        List<Client>clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String city = rs.getString("city");
                String street = rs.getString("street");
                int number = rs.getInt("number");
                String postal = rs.getString("postal_code");

                Address address = new Address(city, street, number, postal);
                Client client = new Client(String.valueOf(id), name, address);
                /// client.setId(String.valueOf(id));

                clients.add(client);
            }
        }
        catch (SQLException e){
            System.out.println("Error fetching clients: " + e.getMessage());
        }
        return clients;
    }

    public Client getClientById(String id){
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                String name = rs.getString("name");
                String city = rs.getString("city");
                String street = rs.getString("street");
                int number = rs.getInt("number");
                String postal = rs.getString("postal_code");

                Address address = new Address(city, street, number, postal);
                Client client = new Client(String.valueOf(id), name, address);
                /// client.setId(String.valueOf(id));
                /*
                int dbId = rs.getInt("id");
                client.setId(String.valueOf(dbId));
                */

                return client;
            }
        }
        catch (SQLException e){
            System.out.println("Error reading client: " + e.getMessage());
        }
        return null;
    }

    public void updateClient(Client client){
        String sql = "UPDATE clients SET name = ?, city = ?, street = ?, number = ?, postal_code = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress().getCity());
            statement.setString(3, client.getAddress().getStreet());
            statement.setInt(4, client.getAddress().getNumber());
            statement.setString(5, client.getAddress().getPostalCode());
            statement.setString(6, client.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0){
                System.out.println("Client updated succesfully.");
            }
            else {
                System.out.println("No client found with the id: " + client.getId());
            }
        }
        catch (SQLException e){
            System.out.println("Error updating client: " + e.getMessage());
        }
    }

    public void deleteClient(String id){
        String sql = "DELETE FROM clients WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0){
                System.out.println("Client deleted succesfully");
            }
            else {
                System.out.println("No clients found with the id: " + id);
            }
        }
        catch (SQLException e){
            System.out.println("Error deleting client: " + e.getMessage());
        }
    }
}
