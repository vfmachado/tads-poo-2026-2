import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingTicket {
    private static final BigDecimal DEFAULT_HOURLY_RATE = new BigDecimal("6.00");

    private final LocalDateTime entryTime;
    private final BigDecimal hourlyRate;
    private LocalDateTime exitTime;

    public ParkingTicket(LocalDateTime entryTime) {
        this(entryTime, DEFAULT_HOURLY_RATE);
    }

    public ParkingTicket(LocalDateTime entryTime, BigDecimal hourlyRate) {
        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time is required.");
        }
        if (hourlyRate == null || hourlyRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Hourly rate must be positive.");
        }
        this.entryTime = entryTime;
        this.hourlyRate = hourlyRate;
    }

    public void close(LocalDateTime exitTime) {
        this.exitTime = validateExitTime(exitTime);
    }

    public BigDecimal calculateTotal() {
        return calculateTotal(this.exitTime);
    }

    public BigDecimal calculateTotal(LocalDateTime exitTime) {
        LocalDateTime validatedExit = validateExitTime(exitTime);
        long minutes = Duration.between(entryTime, validatedExit).toMinutes();
        long hours = (minutes + 59) / 60;
        return hourlyRate.multiply(BigDecimal.valueOf(hours))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    private LocalDateTime validateExitTime(LocalDateTime exitTime) {
        if (exitTime == null) {
            throw new IllegalArgumentException("Exit time is required.");
        }
        if (!exitTime.isAfter(entryTime)) {
            throw new IllegalArgumentException("Exit time must be after entry time.");
        }
        return exitTime;
    }
}
