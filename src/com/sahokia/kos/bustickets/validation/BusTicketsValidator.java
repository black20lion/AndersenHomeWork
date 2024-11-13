package com.sahokia.kos.bustickets.validation;

import com.sahokia.kos.bustickets.exception.PriceException;
import com.sahokia.kos.bustickets.exception.StartDateException;
import com.sahokia.kos.bustickets.exception.TicketClassException;
import com.sahokia.kos.bustickets.exception.TicketTypeException;
import com.sahokia.kos.bustickets.model.BusTicket;
import com.sahokia.kos.bustickets.model.TicketClass;
import com.sahokia.kos.bustickets.model.TicketType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusTicketsValidator {

    public void validateBusTickets(List<BusTicket> busTickets) {
        Map<String, Boolean> current;
        int total = 0;
        int valid = 0;
        Map<String, Integer> violations = new HashMap<>();
        violations.put("ticket class", 0);
        violations.put("ticket type", 0);
        violations.put("price", 0);
        violations.put("start date", 0);
        for (BusTicket busTicket : busTickets) {
            total++;
            current = validateBusTicket(busTicket);
            if (!current.containsValue(true)) {
                valid++;
            } else {
                current.forEach((key, value) -> {
                    if (value) {
                        violations.put(key, violations.get(key) + 1);
                    }
                });
            }
        }
        System.out.println("Total = " + total);
        System.out.println("Valid = " + valid);
        System.out.println("Most popular violation = " + violations.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null));
    }

    public Map<String, Boolean> validateBusTicket(BusTicket busTicket) {
        Map<String, Boolean> violations = new HashMap<>();
        violations.put("ticket class", false);
        violations.put("ticket type", false);
        violations.put("price", false);
        violations.put("start date", false);
        try {
            validateBusTicketClass(busTicket);
        } catch (TicketClassException ticketClassException) {
            System.out.println(ticketClassException.getMessage());
            violations.put("ticket class", true);
        }

        try {
            validateBusTicketType(busTicket);
        } catch (TicketTypeException ticketTypeException) {
            System.out.println(ticketTypeException.getMessage());
            violations.put("ticket type", true);
        }

        try {
            validatePrice(busTicket);
        } catch (PriceException priceException) {
            System.out.println(priceException.getMessage());
            violations.put("price", true);
        }

        try {
            validateStartDate(busTicket);
        } catch (StartDateException startDateException) {
            System.out.println(startDateException.getMessage());
            violations.put("start date", true);
        }
        return violations;
    }

    private void validateBusTicketClass(BusTicket busTicket) {
        if (busTicket.getTicketClass() == null)
            throw new TicketClassException("Ticket class is null in bus ticket " + busTicket);
        try {
            TicketClass.valueOf(busTicket.getTicketClass().name());
        } catch (IllegalArgumentException e) {
            throw new TicketClassException("Invalid ticket class: " + busTicket.getTicketClass() + " for ticket: " + busTicket);
        }
    }

    private void validateBusTicketType(BusTicket busTicket) {
        if (busTicket.getTicketType() == null)
            throw new TicketTypeException("Ticket type is null in bus ticket " + busTicket);
        try {
            TicketType.valueOf(busTicket.getTicketType().name());
        } catch (IllegalArgumentException e) {
            throw new TicketTypeException("Invalid ticket type: " + busTicket.getTicketType() + " for ticket: " + busTicket);
        }
    }

    private void validateStartDate(BusTicket busTicket) {
        if (busTicket.getTicketType() != null) {
            if ((busTicket.getTicketType().equals(TicketType.DAY) ||
                    busTicket.getTicketType().equals(TicketType.WEEK) ||
                    busTicket.getTicketType().equals(TicketType.YEAR)) && busTicket.getStartDate() == null) throw
                    new StartDateException("Start date is null in bus ticket " + busTicket);
        }
        if (busTicket.getStartDate() != null) {
            if (busTicket.getStartDate().isAfter(LocalDate.now()))
                throw new StartDateException("Whoa, looks like the ticket " + busTicket
                        + " is for the future! Time traveler alert! Ticket issued for " + busTicket.getStartDate()
                        + " — better check your flux capacitor!");
        }
    }

    private void validatePrice(BusTicket busTicket) {
        if (busTicket.getPrice() == null)
            throw new PriceException("Price is null in bus ticket " + busTicket);
        if (busTicket.getPrice().equals(BigDecimal.ZERO)) throw
                new PriceException("Price is zero in ticket " + busTicket);
        if (!busTicket.getPrice().remainder(new BigDecimal("2")).equals(BigDecimal.ZERO)) throw
                new PriceException("Price is not even in ticket " + busTicket);
    }
}
