package exemplos.dubles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// CONCEITO 6a — Dublê do tipo STUB: fornece ao objeto testado uma RESPOSTA controlada.
//
// Restaurante PERGUNTA a hora ao Relogio (uma consulta). O stub responde sempre o que o teste mandar.
// Como Relogio tem um método só, o stub cabe num lambda: () -> hora.
//
// Sem a interface (com LocalTime.now() dentro do Restaurante), este teste passaria de dia e falharia
// de madrugada: seria um teste que depende de QUANDO é executado.
class RestauranteTest {

    @ParameterizedTest(name = "as {0}: aberto = {1}")
    @CsvSource({
            "10:59, false",
            "11:00, true",
            "22:59, true",
            "23:00, false"
    })
    void abreAsOnzeEFechaAsVinteETres(LocalTime hora, boolean abertoEsperado) {
        Relogio relogioParado = () -> hora;
        Restaurante restaurante = new Restaurante(relogioParado);

        assertEquals(abertoEsperado, restaurante.estaAberto());
    }
}
