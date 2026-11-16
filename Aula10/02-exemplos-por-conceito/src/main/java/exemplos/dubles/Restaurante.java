package exemplos.dubles;

import java.time.LocalTime;

public class Restaurante {

    private static final LocalTime ABERTURA = LocalTime.of(11, 0);
    private static final LocalTime FECHAMENTO = LocalTime.of(23, 0);

    private final Relogio relogio;

    public Restaurante(Relogio relogio) {
        this.relogio = relogio;
    }

    /** Aberto das 11:00 (inclusive) às 23:00 (exclusive). */
    public boolean estaAberto() {
        LocalTime agora = relogio.agora();
        return !agora.isBefore(ABERTURA) && agora.isBefore(FECHAMENTO);
    }
}
