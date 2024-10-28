import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;


public class Ticket {
    private static Set<String> existingIDs = new HashSet<>();
    private String id = "";
    private String concertHall = "";
    private String eventCode = "";
    private final long time;
    private boolean isPromo;
    private StadiumSector stadiumSector = StadiumSector.A;
    private float maxAllowedBackpackWeight;
    private final Date creationTime;
    private BigDecimal price = BigDecimal.ZERO;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 4;
    private static SecureRandom random = new SecureRandom();

    // Empty constructor
    public Ticket() {
         do {
             this.id = generateRandomID();
         }
        while (existingIDs.contains(this.id));
        existingIDs.add(this.id);

        this.time = System.currentTimeMillis();
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.setTimeInMillis(time);
        this.creationTime = myCalendar.getTime();
    }

    // Limited constructor
    public Ticket(String concertHall, String eventCode) {
        do {
            this.id = generateRandomID();
        }
        while (existingIDs.contains(this.id));
        existingIDs.add(this.id);

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

        this.time = System.currentTimeMillis();
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.setTimeInMillis(time);
        this.creationTime = myCalendar.getTime();
    }

    // Full constructor
    public Ticket(String concertHall, String eventCode, boolean isPromo,
                  StadiumSector stadiumSector, float maxAllowedBackpackWeight, BigDecimal price) {

        do {
            this.id = generateRandomID();
        }
        while (existingIDs.contains(this.id));
        existingIDs.add(this.id);

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

        this.isPromo = isPromo;
        this.stadiumSector = stadiumSector;

        if (maxAllowedBackpackWeight < 0) {
            throw new IllegalArgumentException("Max allowed backpack weight can not be negative");
        } else {
            DecimalFormat df = new DecimalFormat("#.###");
            String formattedWeight = df.format(maxAllowedBackpackWeight).replaceAll(",",".");
            this.maxAllowedBackpackWeight = Float.parseFloat(formattedWeight);
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can not be negative");
        } else {
            this.price = price;
        }

        this.time = System.currentTimeMillis();
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.setTimeInMillis(time);
        this.creationTime = myCalendar.getTime();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id.length() <= 4) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("ID can not be longer than 4 characters");
        }
    }

    public String getConcertHall() {
        return concertHall;
    }

    public void setConcertHall(String concertHall) {
        if (concertHall.length() <= 10) {
            this.concertHall = concertHall;
        } else {
            throw new IllegalArgumentException("Concert hall string can not be longer than 10 characters");
        }
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        if (eventCode.matches("^\\d{3}$")) {
            this.eventCode = eventCode;
        } else {
            throw new IllegalArgumentException("Event code must be a 3-digit number.");
        }
    }

    public long getTime() {
        return time;
    }

    public boolean getIsPromo() {
        return isPromo;
    }

    public void setIsPromo(boolean isPromo) {
        this.isPromo = isPromo;
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

    public void setMaxAllowedBackpackWeight(float maxAllowedBackpackWeight) {
        if (maxAllowedBackpackWeight < 0) {
            throw new IllegalArgumentException("Max allowed backpack weight can not be negative");
        } else {
            DecimalFormat df = new DecimalFormat("#.###");
            String formattedWeight = df.format(maxAllowedBackpackWeight).replaceAll(",",".");
            this.maxAllowedBackpackWeight = Float.parseFloat(formattedWeight);
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can not be negative");
        } else {
            this.price = price;
        }
    }

    public Date getCreationTime() {
        return creationTime;
    }

    private String generateRandomID() {
        StringBuilder idBuilder = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            idBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return idBuilder.toString();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", concertHall='" + concertHall + '\'' +
                ", eventCode=" + eventCode +
                ", time=" + time +
                ", isPromo=" + isPromo +
                ", stadiumSector=" + stadiumSector +
                ", maxAllowedBackpackWeight=" + maxAllowedBackpackWeight +
                ", price=" + price +
                ", creationTime=" + creationTime +
                '}';
    }
}