package com.sahokia.kos.concerttickets.model;

public class Client extends User {


    public void getTicket(Ticket ticket) {
        System.out.println("I'm a client #" + this.getId() + " and I got a ticket #" + ticket.getId());
    }


    public void shareTicket(Ticket ticket, String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number can't be null");
        }

        System.out.println("Ticket #" + ticket.getId() +
                " has been sent to the phone number: " + phone + " by the client #" + this.getId());
    }

    public void shareTicket(Ticket ticket, String phone, String email) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number can't be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email can't be null");
        }
        System.out.println("Ticket #" + ticket.getId() +
                " has been sent to the phone number: " + phone + " and to the email: " + email +
                " by the client #" + this.getId());
    }
}
