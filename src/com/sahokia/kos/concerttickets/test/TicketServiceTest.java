package com.sahokia.kos.concerttickets.test;

import com.sahokia.kos.concerttickets.model.Admin;
import com.sahokia.kos.concerttickets.model.Client;
import com.sahokia.kos.concerttickets.model.Ticket;
import com.sahokia.kos.concerttickets.model.User;
import com.sahokia.kos.concerttickets.service.TicketService;

public class TicketServiceTest {
    public static void main(String[] args) {
        TicketService ticketService = new TicketService();
        for (int i = 0; i < 10; i++) {
            ticketService.addTicket(ticketService.getRandomTicket());
        }
        ticketService.handleTicket("hello");
        ticketService.handleTicket(ticketService.getTicketStorage().get(2).getId());
        ticketService.print();

        ticketService.getRandomTicket().share("8-800-555-353-35");
        ticketService.getRandomTicket().share("+1 223 234 295", "Microsoft@gmail.com");
        Admin admin = new Admin();
        Client client = new Client();
        admin.printRole();
        client.printRole();
        admin.checkTicket(ticketService.getTicketStorage().get(2), ticketService);
        admin.checkTicket(ticketService.getRandomTicket(), ticketService);
        client.getTicket(ticketService.getRandomTicket(), "8-800-555-35-35");
        Ticket ticketEquals1 = ticketService.getRandomTicket();
        Ticket ticketEquals2 = new Ticket(ticketEquals1.getConcertHall(), ticketEquals1.getEventCode(),
                ticketEquals1.getIsPromo(), ticketEquals1.getStadiumSector(),
                ticketEquals1.getMaxAllowedBackpackWeight(),
                ticketEquals1.getPrice());
        System.out.println(ticketEquals1.equals(ticketEquals2));
        ticketEquals2.setId(ticketEquals1.getId());
        System.out.println(ticketEquals1.equals(ticketEquals2));
    }
}