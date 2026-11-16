package foodexpress.aula10;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

// Frete é uma função pura (entra subtotal, sai frete): o tipo de código MAIS FÁCIL de testar.
// Repare que nenhum teste daqui precisa de dublê, de @BeforeEach nem de System.out.
class FreteTest {

    // O teste mais simples possível: um cenário, uma verificação. É o cenário A do Main.
    @Test
    @DisplayName("ENTREGA abaixo de R$ 100,00 cobra frete fixo de R$ 8,00")
    void entregaAbaixoDoLimiteCobraFreteFixo() {
        // Arrange (preparar)
        BigDecimal subtotal = new BigDecimal("59.70");

        // Act (agir)
        BigDecimal frete = Frete.ENTREGA.para(subtotal);

        // Assert (verificar)
        assertEquals(new BigDecimal("8.00"), frete);
    }

    // VALORES-LIMITE: a regra muda em R$ 100,00, então os casos interessantes são os vizinhos de 100.
    // O Main da Aula 09 testava 59,70 e 120,00 — bem longe da fronteira, onde os erros de ">=" x ">" moram.
    @ParameterizedTest(name = "subtotal {0} -> frete {1}")
    @CsvSource({
            "0.00,   8.00",
            "99.99,  8.00",
            "100.00, 0.00",
            "100.01, 0.00"
    })
    @DisplayName("ENTREGA tem frete gratis a partir de R$ 100,00 (inclusive)")
    void entregaTemFreteGratisAPartirDeCemReais(BigDecimal subtotal, BigDecimal freteEsperado) {
        assertEquals(freteEsperado, Frete.ENTREGA.para(subtotal));
    }

    // As duas últimas linhas existem por causa do ARREDONDAMENTO: 2% de 10,25 é 0,205 (sobe para 0,21)
    // e 2% de 10,24 é 0,2048 (desce para 0,20).
    @ParameterizedTest(name = "subtotal {0} -> frete {1}")
    @CsvSource({
            "0.00,  15.00",
            "80.00, 16.60",
            "10.25, 15.21",
            "10.24, 15.20"
    })
    @DisplayName("EXPRESSA cobra R$ 15,00 mais 2% do subtotal, arredondando meio centavo para cima")
    void expressaCobraTaxaFixaMaisPercentual(BigDecimal subtotal, BigDecimal freteEsperado) {
        assertEquals(freteEsperado, Frete.EXPRESSA.para(subtotal));
    }

    @ParameterizedTest(name = "subtotal {0}")
    @ValueSource(strings = {"0.00", "40.00", "200.00"})
    @DisplayName("RETIRADA nunca cobra frete")
    void retiradaNuncaCobraFrete(String subtotal) {
        assertEquals(new BigDecimal("0.00"), Frete.RETIRADA.para(new BigDecimal(subtotal)));
    }

    // Teste de CONTRATO: vale para TODAS as constantes do enum, inclusive as que ainda não existem.
    // Quando alguém acrescentar AGENDADA (mudança M1 da Aula 09), este teste já a cobre sem ser editado.
    // É o item 7 do checklist da Aula 09 ("o contrato está escrito?") virando código executável.
    @ParameterizedTest
    @EnumSource(Frete.class)
    @DisplayName("todo frete e um valor nao negativo com duas casas decimais")
    void todoFreteRespeitaOContrato(Frete frete) {
        BigDecimal valor = frete.para(new BigDecimal("50.00"));

        assertAll(
                () -> assertTrue(valor.signum() >= 0, "frete negativo: " + valor),
                () -> assertEquals(2, valor.scale(), "frete sem duas casas decimais: " + valor));
    }
}
