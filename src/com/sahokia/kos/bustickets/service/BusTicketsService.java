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
    private List<BusTicket> busTicketsStorage = new ArrayList<>();
    private BusTicketsValidator busTicketsValidator = new BusTicketsValidator();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void mapTicketsFromFile(String filePath) {
        List<BusTicket> busTickets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length == 4) {
                    String ticketClassStr = fields[0].trim();
                    String ticketTypeStr = fields[1].trim();
                    String startDateStr = fields[2].trim();
                    String priceStr = fields[3].trim();

                    TicketClass ticketClass = null;
                    TicketType ticketType = null;
                    BigDecimal price = null;
                    LocalDate startDate = null;

                    if (!ticketClassStr.equals("null") && !ticketClassStr.isEmpty()) {
                        try {
                            ticketClass = TicketClass.valueOf(ticketClassStr);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid ticket class: " + ticketClassStr);
                        }
                    }

                    if (!ticketTypeStr.equals("null") && !ticketTypeStr.isEmpty()) {
                        try {
                            ticketType = TicketType.valueOf(ticketTypeStr);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid ticket type: " + ticketTypeStr);
                        }
                    }

                    if (!priceStr.equals("null") && !priceStr.isEmpty()) {
                        try {
                            price = new BigDecimal(priceStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid price format: " + priceStr);
                        }
                    }

                    if (!startDateStr.equals("null") && !startDateStr.isEmpty()) {
                        try {
                            startDate = LocalDate.parse(startDateStr, dateFormatter);
                        } catch (Exception e) {
                            System.out.println("Invalid start date format: " + startDateStr);
                        }
                    }
                    BusTicket ticket = new BusTicket(ticketClass, ticketType, startDate, price);
                    busTickets.add(ticket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        busTicketsStorage.addAll(busTickets);
    }

    public List<BusTicket> getBusTicketsStorage() {
        return busTicketsStorage;
    }

    public void validateBusTickets() {
        this.busTicketsValidator.validateBusTickets(this.busTicketsStorage);
    }
}