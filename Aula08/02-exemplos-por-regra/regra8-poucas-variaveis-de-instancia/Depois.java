import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// [R8] cumprida: os grupos viraram conceitos reconhecidos pelo negócio
// (Periodo e Quarto) e ganharam comportamento (noites() é de Periodo, não de Reserva).
// Teste do agrupamento: um recepcionista reconheceria "Periodo" e "Quarto"? Sim. "DadosDaReserva"? Não.
public class Depois {

    record Periodo(LocalDate checkIn, LocalDate checkOut) {
        Periodo {
            if (!checkOut.isAfter(checkIn)) {
                throw new IllegalArgumentException("Check-out deve ser depois do check-in.");
            }
        }

        long noites() {
            return ChronoUnit.DAYS.between(checkIn, checkOut);
        }
    }

    record Quarto(int numero, String tipo) {
    }

    static final class Reserva {
        private final String hospede;
        private final Periodo periodo;
        private final Quarto quarto;

        // 3 campos: hospede (String) ainda deveria virar tipo próprio (R3); mantido para a demo ficar curta.
        Reserva(String hospede, Periodo periodo, Quarto quarto) {
            this.hospede = hospede;
            this.periodo = periodo;
            this.quarto = quarto;
        }

        String descricao() {
            return hospede + ", quarto " + quarto.numero() + ", " + periodo.noites() + " noites";
        }
    }

    public static void main(String[] args) {
        Periodo periodo = new Periodo(LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 4));
        System.out.println(new Reserva("Ana", periodo, new Quarto(203, "SUITE")).descricao());
    }
}
