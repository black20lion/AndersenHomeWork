package com.sahokia.kos.hibernatehomework.model;


import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;


import java.time.LocalDate;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated
    @Type(PostgreSQLEnumType.class)
    @Column(name = "ticket_type", columnDefinition = "ticket_type")
    private TicketType ticketType;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    public Ticket() {
    }

    public Ticket(Customer customer, TicketType ticketType) {
        this.customer = customer;
        this.ticketType = ticketType;
        this.creationDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user=" + customer +
                ", ticketType=" + ticketType +
                ", creationDate=" + creationDate +
                '}';
    }
}