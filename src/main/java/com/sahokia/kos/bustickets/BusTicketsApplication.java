package com.sahokia.kos.bustickets;

import com.sahokia.kos.bustickets.model.BusTicket;
import com.sahokia.kos.bustickets.service.BusTicketsService;

import java.util.List;

public class BusTicketsApplication {
    private static final String FILE_PATH = "src/resources/BusTickets.txt";

    public static void main(String[] args) {
        BusTicketsService busTicketsService = new BusTicketsService();
        List<BusTicket> busTickets = busTicketsService.mapTicketsFromFile(FILE_PATH);
        busTicketsService.validateBusTickets(busTickets);
    }
}
