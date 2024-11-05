package com.sahokia.kos.concerttickets.model;

import com.sahokia.kos.concerttickets.service.TicketService;

public class Admin extends User {


    public void checkTicket(Ticket ticket, TicketService ticketService) {
        if (ticket == null) {
            throw new IllegalArgumentException("ticket can not be null");
        }
        if (ticketService == null) {
            throw new IllegalArgumentException("ticket service can not be null");
        }
        if (ticketService.getTicketStorage().contains(ticket)) {
            System.out.println("Ticket is valid");
        } else {
            System.out.println("Ticket is invalid");
        }
    }
}