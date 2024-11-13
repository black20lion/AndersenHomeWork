package com.sahokia.kos.concerttickets.model;
import java.util.List;

public class Admin extends User {
    private List<Ticket> ticketStorage;

    public Admin(List<Ticket> ticketStorage) {
        this.ticketStorage = ticketStorage;
    }

    public void checkTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("ticket can not be null");
        }

        if (ticketStorage.contains(ticket)) {
            System.out.println("Admin#" + this.id + ": Ticket #" + ticket.getId() + " is valid");
        } else {
            System.out.println("Admin#" + this.id + ": Ticket #" + ticket.getId() + " is invalid");
        }
    }
}