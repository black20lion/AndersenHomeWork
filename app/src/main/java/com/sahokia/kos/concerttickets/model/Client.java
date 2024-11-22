package com.sahokia.kos.concerttickets.model;

public class Client extends User {
    Ticket ticket;

    public Client(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return this.ticket;
    }


    public void shareTicket(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number can't be null");
        }

        System.out.println("Ticket #" + this.ticket.getId() +
                " has been sent to the phone number: " + phone + " by the client #" + this.getId());
    }

    public void shareTicket(String phone, String email) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number can't be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email can't be null");
        }
        System.out.println("Ticket #" + this.ticket.getId() +
                " has been sent to the phone number: " + phone + " and to the email: " + email +
                " by the client #" + this.getId());
    }
}
