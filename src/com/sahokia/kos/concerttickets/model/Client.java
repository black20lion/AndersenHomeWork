package com.sahokia.kos.concerttickets.model;

public class Client extends User {

    public void getTicket(Ticket ticket, String phone) {
        ticket.share(phone);
    }

    public void getTicket(Ticket ticket, String phone, String email) {
        ticket.share(phone, email);
    }
}
