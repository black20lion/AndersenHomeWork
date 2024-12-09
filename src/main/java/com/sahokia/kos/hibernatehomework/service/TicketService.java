package com.sahokia.kos.hibernatehomework.service;

import com.sahokia.kos.hibernatehomework.dao.TicketDao;
import com.sahokia.kos.hibernatehomework.dao.CustomerDao;
import com.sahokia.kos.hibernatehomework.model.Customer;
import com.sahokia.kos.hibernatehomework.model.Ticket;
import com.sahokia.kos.hibernatehomework.model.TicketType;

import java.util.List;

public class TicketService {
    private final TicketDao ticketDao;
    private final CustomerDao customerDao;

    public TicketService(TicketDao ticketDao, CustomerDao customerDao) {
        this.ticketDao = ticketDao;
        this.customerDao = customerDao;
    }

    public Ticket getTicketById(int ticketId) {
        return ticketDao.findById(ticketId);
    }

    public void createTicket(Ticket ticket) {
        ticketDao.saveTicket(ticket);
    }

    public void deleteTicketById(int ticketId) {
        ticketDao.deleteTicket(ticketId);
    }

    public void updateTicketType(int ticketId, TicketType newType) {
        ticketDao.updateTicketType(ticketId, newType);
    }

    public List<Ticket> getTicketsByCustomerId(int userId) {
        return ticketDao.findTicketsByUserId(userId);
    }

    public Customer getCustomerById(int userId) {
        return customerDao.findById(userId);
    }

    public void createCustomer(Customer customer) {
        customerDao.saveCustomer(customer);
    }

    public void deleteCustomerById(int userId) {
        customerDao.deleteCustomer(userId);
    }

    public void createTicketForCustomer(int userId, Ticket ticket) {
        Customer customer = customerDao.findById(userId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id " + userId + " not found.");
        }
        ticket.setCustomer(customer);
        ticketDao.saveTicket(ticket);
    }

    public void deleteAllTicketsByCustomerId(int userId) {
        List<Ticket> tickets = ticketDao.findTicketsByUserId(userId);
        for (Ticket ticket : tickets) {
            ticketDao.deleteTicket(ticket.getId());
        }
    }

    public void updateCustomerAndTickets(int customerId, String newName, List<Ticket> tickets) {
        Customer customer = customerDao.findById(customerId);
        if (customer != null) {
            customer.setName(newName);
            customerDao.updateCustomerAndTickets(customer, tickets);
        } else {
            System.out.println("Customer with this id number does not exist");
        }
    }
}