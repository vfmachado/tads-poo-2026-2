import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime entryTime = LocalDateTime.of(2026, 2, 23, 8, 10);
        LocalDateTime exitTime = LocalDateTime.of(2026, 2, 23, 11, 45);

        ParkingTicket ticket = new ParkingTicket(entryTime);
        ticket.close(exitTime);

        System.out.println("Total to pay: " + ticket.calculateTotal());
    }
}
