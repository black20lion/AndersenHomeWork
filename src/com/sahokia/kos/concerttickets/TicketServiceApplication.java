package com.sahokia.kos.concerttickets;

import com.sahokia.kos.concerttickets.model.Admin;
import com.sahokia.kos.concerttickets.model.Client;
import com.sahokia.kos.concerttickets.model.Ticket;
import com.sahokia.kos.concerttickets.service.TicketService;

public class TicketServiceApplication {
    public static void main(String[] args) {
        TicketService ticketService = new TicketService();
        for (int i = 0; i < 10; i++) {
            ticketService.addTicket(ticketService.generateRandomTicket());
        }
        ticketService.handleTicket("hello");
        ticketService.handleTicket(ticketService.getTicketStorage().get(2).getId());
        ticketService.print();

        Admin admin = new Admin(ticketService.getTicketStorage());
        Client client = new Client();
        admin.printRole();
        client.printRole();
        admin.checkTicket(ticketService.getTicketStorage().get(2));
        admin.checkTicket(ticketService.generateRandomTicket());
        client.shareTicket(ticketService.generateRandomTicket(), "8-800-555-35-35");
        client.shareTicket(ticketService.generateRandomTicket(), "8-800-555-35-35", "bestemail@gmail.com");
        client.getTicket(ticketService.generateRandomTicket());

        Ticket ticketEquals1 = ticketService.generateRandomTicket();
        Ticket ticketEquals2 = new Ticket(ticketEquals1.getConcertHall(), ticketEquals1.getEventCode(),
                ticketEquals1.getIsPromo(), ticketEquals1.getStadiumSector(),
                ticketEquals1.getMaxAllowedBackpackWeight(),
                ticketEquals1.getPrice());
        System.out.println(ticketEquals1.equals(ticketEquals2));
        ticketEquals2.setId(ticketEquals1.getId());

        System.out.println(ticketEquals1);
        System.out.println(ticketEquals2);
        System.out.println(ticketEquals1.equals(ticketEquals2));
    }
}