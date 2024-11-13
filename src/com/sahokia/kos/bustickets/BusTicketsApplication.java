package com.sahokia.kos.bustickets;

import com.sahokia.kos.bustickets.service.BusTicketsService;

public class BusTicketsApplication {
    public static void main(String[] args) {
        BusTicketsService busTicketsService = new BusTicketsService();
        String filePath = "src/com/sahokia/kos/bustickets/data/BusTickets.txt";
        busTicketsService.mapTicketsFromFile(filePath);
        busTicketsService.validateBusTickets();
    }
}
