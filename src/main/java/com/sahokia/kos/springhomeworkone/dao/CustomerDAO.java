package com.sahokia.kos.springhomeworkone.dao;

import com.sahokia.kos.springhomeworkone.model.Customer;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.Instant;
import java.util.Optional;

@Component
public class CustomerDAO {

    private final String SAVE_CUSTOMER_QUERY = "INSERT INTO \"customer\" (name, creation_date) VALUES (?, ?)";
    private final String GET_CUSTOMER_BY_ID_QUERY = "SELECT * FROM \"customer\" WHERE id = ?";
    private final String DELETE_CUSTOMER_QUERY = "DELETE FROM \"customer\" WHERE id = ?";

    private final PGSimpleDataSource dataSource;

    public CustomerDAO(PGSimpleDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveCustomer(String name, Instant creationDate) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_CUSTOMER_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setTimestamp(2, Timestamp.from(creationDate));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Customer> getCustomerById(int customerId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CUSTOMER_BY_ID_QUERY)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Timestamp creationDate = resultSet.getTimestamp("creation_date");
                    Customer customer = new Customer(id, name, creationDate.toInstant());
                    return Optional.of(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deleteCustomerById(int customerId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUSTOMER_QUERY)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
