import java.math.BigDecimal;

public class TicketService {
    public static void main (String[] args) {
        Ticket testTicket1 = new Ticket();
        Ticket testTicket2 = new Ticket("MILO HALL", "312");
        Ticket testTicket3 = new Ticket("MSG",
                "999", true,
                StadiumSector.C, 12.9123412f, new BigDecimal("56567365"));

        System.out.println(testTicket1);
        System.out.println(testTicket2);
        System.out.println(testTicket3);
    }
}
