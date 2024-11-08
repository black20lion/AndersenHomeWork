package com.sahokia.kos.concerttickets.service;

import com.sahokia.kos.concerttickets.model.IdentifiableEntity;
import com.sahokia.kos.concerttickets.interfaces.Printable;
import com.sahokia.kos.concerttickets.model.StadiumSector;
import com.sahokia.kos.concerttickets.model.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.UUID;

public class TicketService extends IdentifiableEntity implements Printable {
    private static final Set<String> POSSIBLE_HALLS = new HashSet<>();
    private static final Random RANDOM_GENERATOR = new Random();
    private static final float MAX_WEIGHT = 50.0f;
    private static final float MIN_WEIGHT = 0.0f;
    private static final BigDecimal MAX_PRICE = new BigDecimal("100000");
    private static final BigDecimal MIN_PRICE = new BigDecimal("1");
    private static final String MESSAGE_IF_NO_HALLS = "No halls available";

    static {
        POSSIBLE_HALLS.add("Mega Hall");
        POSSIBLE_HALLS.add("Super Hall");
        POSSIBLE_HALLS.add("Extra Hall");
    }

    private final List<Ticket> ticketStorage;

    public TicketService() {
        this.id = UUID.randomUUID().toString();
        this.ticketStorage = new ArrayList<>();
    }

    @Override
    public void print() {
        for (Ticket ticket : this.ticketStorage) {
            ticket.print();
            System.out.println("===========================================");
        }
    }

    public List<Ticket> getTicketStorage() {
        return this.ticketStorage;
    }

    public void addTicket(Ticket ticket) {
        this.ticketStorage.add(ticket);
    }

    public Ticket generateRandomTicket() {
        return new Ticket(
                getRandomConcertHall(POSSIBLE_HALLS),
                getRandomEventCode(),
                RANDOM_GENERATOR.nextBoolean(),
                StadiumSector.values()[RANDOM_GENERATOR.nextInt(StadiumSector.values().length)],
                getRandomBackpackWeight(MIN_WEIGHT, MAX_WEIGHT),
                getRandomBigDecimal(MIN_PRICE, MAX_PRICE)
        );
    }

    public void handleTicket(String id) {
        Optional<Ticket> processing_ticket = getTicketById(id);
        processing_ticket.ifPresentOrElse(
                this::processTicket,
                () -> System.out.println("com.sahokia.kos.concerttickets.model.Ticket with id "
                        + id + " is not found")
        );
    }

    private Optional<Ticket> getTicketById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (ticketStorage == null) {
            throw new IllegalStateException("com.sahokia.kos.concerttickets.model.Ticket storage has not been initialized");
        }
        return ticketStorage.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst();
    }

    private void processTicket(Ticket ticket) {
        System.out.println("com.sahokia.kos.concerttickets.model.Ticket request result: " + ticket);
    }


    private static String getRandomConcertHall(Set<String> set) {
        if (set.isEmpty()) {
            return MESSAGE_IF_NO_HALLS;
        }
        Optional<String> selectedHall = set.stream().skip(RANDOM_GENERATOR.nextInt(set.size())).findFirst();
        return selectedHall.orElse(MESSAGE_IF_NO_HALLS);
    }

    private String getRandomEventCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int digit = RANDOM_GENERATOR.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    private float getRandomBackpackWeight(float min, float max) {
        if (min < 0 || max < 0) {
            throw new IllegalArgumentException("Weight must be non-negative.");
        }
        if (min >= max) {
            throw new IllegalArgumentException("Min weight must be less than max weight.");
        }
        return min + RANDOM_GENERATOR.nextFloat() * (max - min);
    }

    private BigDecimal getRandomBigDecimal(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice.compareTo(BigDecimal.ZERO) < 0 || maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be non-negative.");
        }
        if (minPrice.compareTo(maxPrice) >= 0) {
            throw new IllegalArgumentException("Min price must be less than max price.");
        }

        BigDecimal randomValue = BigDecimal.valueOf(RANDOM_GENERATOR.nextDouble());
        BigDecimal result = minPrice.add(randomValue.multiply(maxPrice.subtract(minPrice)));
        return result.setScale(2, RoundingMode.HALF_UP);
    }
}