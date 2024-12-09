package com.sahokia.kos.hibernatehomework;

import com.sahokia.kos.hibernatehomework.dao.TicketDao;
import com.sahokia.kos.hibernatehomework.dao.CustomerDao;
import com.sahokia.kos.hibernatehomework.model.Customer;
import com.sahokia.kos.hibernatehomework.model.Ticket;
import com.sahokia.kos.hibernatehomework.model.TicketType;
import com.sahokia.kos.hibernatehomework.service.TicketService;

import java.util.ArrayList;
import java.util.List;

public class HibernateHomeworkApplication {
    public static void main(String[] args) {
        TicketService ticketService = new TicketService(new TicketDao(), new CustomerDao());
        System.out.println(ticketService.getCustomerById(12));
//        System.out.println(ticketService.getTicketById(5));
//        ticketService.createTicket(new Ticket(new Customer("Misha"), TicketType.WEEK));
//        ticketService.createCustomer(new Customer("Maksim"));
//        ticketService.deleteTicketById(4);
//        ticketService.updateTicketType(3, TicketType.DAY);
//        ticketService.deleteCustomerById(7);
//        List<Ticket> tickets = new ArrayList<>();
//        tickets.add(new Ticket(new Customer("Alex"), TicketType.MONTH));
//        tickets.add(new Ticket(new Customer("Maria"), TicketType.DAY));
//        tickets.add(new Ticket(new Customer("Kirill"), TicketType.WEEK));
//        ticketService.updateCustomerAndTickets(12, "Boris", tickets);
    }
}
