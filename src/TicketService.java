import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class TicketService {
    private static final Set<String> POSSIBLE_HALLS = new HashSet<>();
    private static final List<Ticket> TICKET_STORAGE = new ArrayList<>();
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

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            TICKET_STORAGE.add(new Ticket(
                    getRandomConcertHall(POSSIBLE_HALLS),
                    getRandomEventCode(),
                    RANDOM_GENERATOR.nextBoolean(),
                    StadiumSector.values()[RANDOM_GENERATOR.nextInt(StadiumSector.values().length)],
                    getRandomBackpackWeight(MIN_WEIGHT, MAX_WEIGHT),
                    getRandomBigDecimal(MIN_PRICE, MAX_PRICE)
            ));
        }
        for (Ticket ticket : TICKET_STORAGE) {
            System.out.println(ticket);
        }
    }

    private static String getRandomConcertHall(Set<String> set) {
        if (set.isEmpty()) {
            return MESSAGE_IF_NO_HALLS;
        }
        Optional<String> selectedHall = set.stream().skip(RANDOM_GENERATOR.nextInt(set.size())).findFirst();
        return selectedHall.orElse(MESSAGE_IF_NO_HALLS);
    }

    private static String getRandomEventCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int digit = RANDOM_GENERATOR.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    private static float getRandomBackpackWeight(float min, float max) {
        if (min < 0 || max < 0) {
            throw new IllegalArgumentException("Weight must be non-negative.");
        }
        if (min >= max) {
            throw new IllegalArgumentException("Min weight must be less than max weight.");
        }
        return min + RANDOM_GENERATOR.nextFloat() * (max - min);
    }

    private static BigDecimal getRandomBigDecimal(BigDecimal minPrice, BigDecimal maxPrice) {
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