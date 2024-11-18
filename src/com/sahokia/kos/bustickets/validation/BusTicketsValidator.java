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
    private static final String TICKET_CLASS_VIOLATION_NAME = "ticket class";
    private static final String TICKET_TYPE_VIOLATION_NAME = "ticket type";
    private static final String PRICE_VIOLATION_NAME = "price";
    private static final String START_DATE_VIOLATION_NAME = "start date";

    public void validateBusTickets(List<BusTicket> busTickets) {
        Map<String, Boolean> currentBusTicketViolations;
        int valid = 0;
        Map<String, Integer> violations = new HashMap<>();
        violations.put(TICKET_CLASS_VIOLATION_NAME, 0);
        violations.put(TICKET_TYPE_VIOLATION_NAME, 0);
        violations.put(PRICE_VIOLATION_NAME, 0);
        violations.put(START_DATE_VIOLATION_NAME, 0);
        for (BusTicket busTicket : busTickets) {
            currentBusTicketViolations = validateBusTicket(busTicket);
            if (!currentBusTicketViolations.containsValue(true)) {
                valid++;
            } else {
                currentBusTicketViolations.forEach((key, value) -> {
                    if (value) {
                        violations.put(key, violations.get(key) + 1);
                    }
                });
            }
        }

        String mostPopularViolation = violations.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println("Total = " + busTickets.size());
        System.out.println("Valid = " + valid);
        System.out.println("Most popular violation = " + mostPopularViolation);
    }

    public Map<String, Boolean> validateBusTicket(BusTicket busTicket) {
        Map<String, Boolean> violations = new HashMap<>();
        violations.put(TICKET_CLASS_VIOLATION_NAME, false);
        violations.put(TICKET_TYPE_VIOLATION_NAME, false);
        violations.put(PRICE_VIOLATION_NAME, false);
        violations.put(START_DATE_VIOLATION_NAME, false);
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

    private void validateBusTicketClass(BusTicket busTicket) throws TicketClassException {
        if (busTicket.getTicketClass() == null)
            throw new TicketClassException("Ticket class is null in bus ticket " + busTicket);
        try {
            TicketClass.valueOf(busTicket.getTicketClass().name());
        } catch (IllegalArgumentException e) {
            throw new TicketClassException("Invalid ticket class: " + busTicket.getTicketClass() + " for ticket: " + busTicket);
        }
    }

    private void validateBusTicketType(BusTicket busTicket) throws TicketTypeException {
        if (busTicket.getTicketType() == null)
            throw new TicketTypeException("Ticket type is null in bus ticket " + busTicket);
        try {
            TicketType.valueOf(busTicket.getTicketType().name());
        } catch (IllegalArgumentException e) {
            throw new TicketTypeException("Invalid ticket type: " + busTicket.getTicketType() + " for ticket: " + busTicket);
        }
    }

    private void validateStartDate(BusTicket busTicket) throws StartDateException {
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

    private void validatePrice(BusTicket busTicket) throws PriceException {
        if (busTicket.getPrice() == null)
            throw new PriceException("Price is null in bus ticket " + busTicket);
        if (busTicket.getPrice().equals(BigDecimal.ZERO)) throw
                new PriceException("Price is zero in ticket " + busTicket);
        if (!busTicket.getPrice().remainder(new BigDecimal("2")).equals(BigDecimal.ZERO)) throw
                new PriceException("Price is not even in ticket " + busTicket);
    }
}
