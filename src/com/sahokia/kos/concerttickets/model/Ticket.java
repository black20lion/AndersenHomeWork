package com.sahokia.kos.concerttickets.model;

import com.sahokia.kos.concerttickets.interfaces.Printable;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Ticket extends IdentifiableEntity implements Printable {
    private static final Set<String> EXISTING_ID_S = new HashSet<>();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String DEFAULT_STRING_IF_EMPTY = "";
    private static final int ID_LENGTH = 4;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Tbilisi");
    private final LocalDateTime creationDateTime;
    private String concertHall;
    private String eventCode;
    private int time;
    private boolean isPromo;
    private StadiumSector stadiumSector = StadiumSector.A;
    private float maxAllowedBackpackWeight;
    private BigDecimal price;

    public Ticket() {
        do {
            this.id = generateRandomID();
        }
        while (EXISTING_ID_S.contains(this.getId()));
        EXISTING_ID_S.add(this.getId());
        this.time = (int) LocalDateTime.now().atZone(ZONE_ID).toEpochSecond();
        this.creationDateTime = LocalDateTime.now();
        this.concertHall = DEFAULT_STRING_IF_EMPTY;
        this.eventCode = DEFAULT_STRING_IF_EMPTY;
        this.price = BigDecimal.ZERO;
    }

    public Ticket(String concertHall, String eventCode) {
        this();
        if (concertHall.length() <= 10) {
            this.concertHall = concertHall;
        } else {
            throw new IllegalArgumentException("Concert hall string can not be longer than 10 characters");
        }

        if (eventCode.matches("^\\d{3}$")) {
            this.eventCode = eventCode;
        } else {
            throw new IllegalArgumentException("Event code must be a 3-digit number.");
        }
    }

    public Ticket(String concertHall, String eventCode, boolean isPromo,
                  StadiumSector stadiumSector, float maxAllowedBackpackWeight, BigDecimal price) {
        this(concertHall, eventCode);
        this.isPromo = isPromo;
        this.stadiumSector = stadiumSector;

        if (maxAllowedBackpackWeight < 0) {
            throw new IllegalArgumentException("Max allowed backpack weight can not be negative");
        } else {
            DecimalFormat df = new DecimalFormat("#.###");
            String formattedWeight = df.format(maxAllowedBackpackWeight).replaceAll(",", ".");
            this.maxAllowedBackpackWeight = Float.parseFloat(formattedWeight);
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can not be negative");
        } else {
            this.price = price;
        }
    }

    @Override
    public void setId(String id) {
        if (id.length() <= 4) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("ID can not be longer than 4 characters");
        }
    }

    @Override
    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "com.sahokia.kos.concerttickets.model.Ticket{" +
                "id='" + this.getId() + '\'' +
                ", concertHall='" + concertHall + '\'' +
                ", eventCode=" + eventCode +
                ", time=" + time +
                ", isPromo=" + isPromo +
                ", stadiumSector=" + stadiumSector +
                ", maxAllowedBackpackWeight=" + maxAllowedBackpackWeight +
                ", price=" + price +
                ", creationTime=" + creationDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return this.id.equals(ticket.getId()) && getTime() == ticket.getTime() && isPromo == ticket.isPromo
                && Float.compare(getMaxAllowedBackpackWeight(), ticket.getMaxAllowedBackpackWeight()) == 0
                && Objects.equals(getCreationDateTime(), ticket.getCreationDateTime())
                && Objects.equals(getConcertHall(), ticket.getConcertHall())
                && Objects.equals(getEventCode(), ticket.getEventCode())
                && getStadiumSector() == ticket.getStadiumSector()
                && Objects.equals(getPrice(), ticket.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), getCreationDateTime(),
                getConcertHall(), getEventCode(),
                getTime(), isPromo, getStadiumSector(),
                getMaxAllowedBackpackWeight(), getPrice());
    }

    public String getConcertHall() {
        return concertHall;
    }

    public String getEventCode() {
        return eventCode;
    }

    public long getTime() {
        return time;
    }

    public long setTime(long time) {
        return this.time = (int) time;
    }

    public boolean getIsPromo() {
        return isPromo;
    }

    public StadiumSector getStadiumSector() {
        return stadiumSector;
    }

    public void setStadiumSector(StadiumSector stadiumSector) {
        this.stadiumSector = stadiumSector;
    }

    public float getMaxAllowedBackpackWeight() {
        return maxAllowedBackpackWeight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    private String generateRandomID() {
        StringBuilder idBuilder = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            idBuilder.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
        return idBuilder.toString();
    }
}