package com.sahokia.kos.springhomeworkone.service;

import com.sahokia.kos.springhomeworkone.dao.CustomerDAO;
import com.sahokia.kos.springhomeworkone.dao.TicketDAO;
import com.sahokia.kos.springhomeworkone.model.Customer;
import com.sahokia.kos.springhomeworkone.model.Ticket;
import com.sahokia.kos.springhomeworkone.model.TicketType;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final CustomerDAO customerDAO;
    private final TicketDAO ticketDAO;

    public TicketService(CustomerDAO customerDAO, TicketDAO ticketDAO) {
        this.customerDAO = customerDAO;
        this.ticketDAO = ticketDAO;
    }

    public void saveCustomer(String name) {
        customerDAO.saveCustomer(name, Instant.now());
    }

    public Optional<Customer> getCustomerById(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }

    public void deleteCustomerById(int customerId) {
        customerDAO.deleteCustomerById(customerId);
    }

    public void saveTicket(int customerId, TicketType ticketType) {
        ticketDAO.saveTicket(customerId, ticketType, Instant.now());
    }

    public List<Ticket> getTicketsByCustomerId(int customerId) {
        return ticketDAO.getTicketsByCustomerId(customerId);
    }

    public void updateTicketType(int ticketId, TicketType newTicketType) {
        ticketDAO.updateTicketType(ticketId, newTicketType);
    }

    public void deleteTicketsByCustomerId(int customerId) {
        ticketDAO.deleteTicketsByCustomerId(customerId);
    }

    public void deleteCustomerAndTickets(int customerId) {
        ticketDAO.deleteTicketsByCustomerId(customerId);
        customerDAO.deleteCustomerById(customerId);
    }
}
