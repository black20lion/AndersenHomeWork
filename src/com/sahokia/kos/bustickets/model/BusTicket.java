package com.sahokia.kos.bustickets.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BusTicket {
    private TicketClass ticketClass;
    private TicketType ticketType;
    private LocalDate startDate;
    private BigDecimal price;

    public BusTicket(TicketClass ticketClass, TicketType ticketType, LocalDate startDate, BigDecimal price) {
        this.ticketClass = ticketClass;
        this.ticketType = ticketType;
        this.startDate = startDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return "BusTicket{" +
                "ticketClass=" + ticketClass +
                ", ticketType=" + ticketType +
                ", startDate=" + startDate +
                ", price=" + price +
                '}';
    }

    public TicketClass getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(TicketClass ticketClass) {
        this.ticketClass = ticketClass;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
