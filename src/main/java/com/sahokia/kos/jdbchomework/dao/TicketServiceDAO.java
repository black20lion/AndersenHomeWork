package com.sahokia.kos.jdbchomework.dao;

import com.sahokia.kos.jdbchomework.connection.DatabaseConnection;
import com.sahokia.kos.jdbchomework.model.Ticket;
import com.sahokia.kos.jdbchomework.model.TicketType;
import com.sahokia.kos.jdbchomework.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketServiceDAO {

    private final String SAVE_USER_QUERY = "INSERT INTO \"user\" (name, creation_date) VALUES (?, ?)";
    private final String SAVE_TICKET_QUERY = "INSERT INTO \"ticket\" (user_id, ticket_type, creation_date) VALUES (?, ?::ticket_type, ?)";
    private final String GET_USER_BY_ID_QUERY = "SELECT * FROM \"user\" WHERE id = ?";
    private final String GET_TICKETS_BY_USER_ID_QUERY = "SELECT * FROM \"ticket\" WHERE user_id = ?";
    private final String UPDATE_TICKET_TYPE_QUERY = "UPDATE \"ticket\" SET ticket_type = ?::ticket_type WHERE id = ?";
    private final String DELETE_TICKETS_QUERY = "DELETE FROM \"ticket\" WHERE user_id = ?";
    private final String DELETE_USER_QUERY = "DELETE FROM \"user\" WHERE id = ?";


    public void saveUser(String name, Date creationDate) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, creationDate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveTicket(int userId, TicketType ticketType, Date creationDate) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TICKET_QUERY)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, ticketType.toString());
            preparedStatement.setDate(3, creationDate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> getUserById(int userId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID_QUERY)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Date creationDate = resultSet.getDate("creation_date");
                    User user = new User(id, name, creationDate.toLocalDate());
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TICKETS_BY_USER_ID_QUERY)) {

            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int userIdGetted = resultSet.getInt("user_id");
                    Date creationDate = resultSet.getDate("creation_date");
                    TicketType ticketType = TicketType.valueOf(resultSet.getString("ticket_type"));

                    Ticket ticket = new Ticket(id, userIdGetted, ticketType, creationDate.toLocalDate());
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public void updateTicketType(int ticketId, TicketType newTicketType) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TICKET_TYPE_QUERY)) {

            preparedStatement.setString(1, newTicketType.toString());
            preparedStatement.setInt(2, ticketId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserById(int userId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement deleteTicketsStmt = connection.prepareStatement(DELETE_TICKETS_QUERY);
             PreparedStatement deleteUserStmt = connection.prepareStatement(DELETE_USER_QUERY)) {

            deleteTicketsStmt.setInt(1, userId);
            deleteTicketsStmt.executeUpdate();

            deleteUserStmt.setInt(1, userId);
            deleteUserStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
