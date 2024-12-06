package com.sahokia.kos.bustickets.service;

import com.sahokia.kos.bustickets.model.BusTicket;
import com.sahokia.kos.bustickets.model.TicketClass;
import com.sahokia.kos.bustickets.model.TicketType;
import com.sahokia.kos.bustickets.validation.BusTicketsValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BusTicketsService {
    private BusTicketsValidator busTicketsValidator = new BusTicketsValidator();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<BusTicket> mapTicketsFromFile(String filePath) {
        List<BusTicket> busTickets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                BusTicket ticket = mapLineToTicket(line);
                if (ticket != null) {
                    busTickets.add(ticket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return busTickets;
    }

    private BusTicket mapLineToTicket(String line) {
        String[] fields = line.split(",");
        if (fields.length != 4) {
            System.out.println("Invalid line format: " + line);
            return null;
        }

        String ticketClassStr = fields[0].trim();
        String ticketTypeStr = fields[1].trim();
        String startDateStr = fields[2].trim();
        String priceStr = fields[3].trim();

        TicketClass ticketClass = parseTicketClass(ticketClassStr);
        TicketType ticketType = parseTicketType(ticketTypeStr);
        BigDecimal price = parsePrice(priceStr);
        LocalDate startDate = parseStartDate(startDateStr);

        return new BusTicket(ticketClass, ticketType, startDate, price);
    }

    private TicketClass parseTicketClass(String ticketClassStr) {
        if (isNotNullOrEmpty(ticketClassStr)) {
            try {
                return TicketClass.valueOf(ticketClassStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid ticket class: " + ticketClassStr);
            }
        }
        return null;
    }

    private TicketType parseTicketType(String ticketTypeStr) {
        if (isNotNullOrEmpty(ticketTypeStr)) {
            try {
                return TicketType.valueOf(ticketTypeStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid ticket type: " + ticketTypeStr);
            }
        }
        return null;
    }

    private BigDecimal parsePrice(String priceStr) {
        if (isNotNullOrEmpty(priceStr)) {
            try {
                return new BigDecimal(priceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format: " + priceStr);
            }
        }
        return null;
    }

    private LocalDate parseStartDate(String startDateStr) {
        if (isNotNullOrEmpty(startDateStr)) {
            try {
                return LocalDate.parse(startDateStr, dateFormatter);
            } catch (Exception e) {
                System.out.println("Invalid start date format: " + startDateStr);
            }
        }
        return null;
    }

    private boolean isNotNullOrEmpty(String str) {
        return !str.equals("null") && !str.isEmpty();
    }

    public void validateBusTickets(List<BusTicket> busTickets) {
        this.busTicketsValidator.validateBusTickets(busTickets);
    }
}