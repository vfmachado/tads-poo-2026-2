import java.time.LocalDate;

// Reservas de hotel.
// [R8] violada: cinco campos que formam dois grupos (data clumps):
// checkIn/checkOut (período) e numeroDoQuarto/tipoDoQuarto (quarto).
public class Antes {

    static class Reserva {
        String hospede;
        LocalDate checkIn;
        LocalDate checkOut;
        int numeroDoQuarto;
        String tipoDoQuarto;

        Reserva(String hospede, LocalDate checkIn, LocalDate checkOut, int numeroDoQuarto, String tipoDoQuarto) {
            this.hospede = hospede;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.numeroDoQuarto = numeroDoQuarto;
            this.tipoDoQuarto = tipoDoQuarto;
        }
    }

    public static void main(String[] args) {
        Reserva reserva = new Reserva("Ana", LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 4), 203, "SUITE");
        System.out.println(reserva.hospede + " no quarto " + reserva.numeroDoQuarto);
    }
}
