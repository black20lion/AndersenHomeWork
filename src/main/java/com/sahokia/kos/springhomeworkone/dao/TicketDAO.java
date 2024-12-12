package com.sahokia.kos.springhomeworkone.dao;

import com.sahokia.kos.springhomeworkone.model.Ticket;
import com.sahokia.kos.springhomeworkone.model.TicketType;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketDAO {

    private final String SAVE_TICKET_QUERY = "INSERT INTO \"ticket\" (customer_id, ticket_type, creation_date) VALUES (?, ?::ticket_type, ?)";
    private final String GET_TICKETS_BY_CUSTOMER_ID_QUERY = "SELECT * FROM \"ticket\" WHERE customer_id = ?";
    private final String UPDATE_TICKET_TYPE_QUERY = "UPDATE \"ticket\" SET ticket_type = ?::ticket_type WHERE id = ?";
    private final String DELETE_TICKETS_QUERY = "DELETE FROM \"ticket\" WHERE customer_id = ?";

    private final PGSimpleDataSource dataSource;

    public TicketDAO(PGSimpleDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveTicket(int customerId, TicketType ticketType, Instant creationDate) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TICKET_QUERY)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setString(2, ticketType.toString());
            preparedStatement.setTimestamp(3, Timestamp.from(creationDate));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> getTicketsByCustomerId(int customerId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TICKETS_BY_CUSTOMER_ID_QUERY)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int customerIdGetted = resultSet.getInt("customer_id");
                    Date creationDate = resultSet.getDate("creation_date");
                    TicketType ticketType = TicketType.valueOf(resultSet.getString("ticket_type"));
                    Ticket ticket = new Ticket(id, customerIdGetted, ticketType, creationDate.toInstant());
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public void updateTicketType(int ticketId, TicketType newTicketType) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TICKET_TYPE_QUERY)) {
            preparedStatement.setString(1, newTicketType.toString());
            preparedStatement.setInt(2, ticketId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTicketsByCustomerId(int customerId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TICKETS_QUERY)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
