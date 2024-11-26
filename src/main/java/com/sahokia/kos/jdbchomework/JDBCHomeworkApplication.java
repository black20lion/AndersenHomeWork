package com.sahokia.kos.jdbchomework;

import com.sahokia.kos.jdbchomework.dao.TicketServiceDAO;
import com.sahokia.kos.jdbchomework.model.Ticket;
import com.sahokia.kos.jdbchomework.model.TicketType;
import com.sahokia.kos.jdbchomework.model.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class JDBCHomeworkApplication {
    public static void main(String[] args) {
        TicketServiceDAO ticketServiceDAO = new TicketServiceDAO();
        ticketServiceDAO.saveUser("Vanya", Date.valueOf(LocalDate.now()));
        ticketServiceDAO.saveTicket(7, TicketType.DAY, Date.valueOf(LocalDate.now()));
        ticketServiceDAO.saveTicket(7, TicketType.YEAR, Date.valueOf(LocalDate.now()));
        ticketServiceDAO.saveUser("Tomas", Date.valueOf(LocalDate.now()));
        List<Ticket> vanyaTickets = ticketServiceDAO.getTicketsByUserId(7);
        System.out.println(vanyaTickets);
        ticketServiceDAO.updateTicketType(3, TicketType.WEEK);
        Optional<User> optionalVanya = ticketServiceDAO.getUserById(7);
        User vanya = optionalVanya.orElse(new User(-1, "User not found", LocalDate.now()));
        System.out.println(vanya);
    }
}
