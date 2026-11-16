package foodexpress.aula10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PagamentoTest {

    // 0,30 x 0,95 = 0,285: é o caso que distingue os modos de arredondamento
    // (HALF_UP dá 0,29; HALF_EVEN daria 0,28). Sem ele, trocar o modo passaria despercebido.
    @ParameterizedTest(name = "total {0} -> a pagar {1}")
    @CsvSource({
            "100.00, 95.00",
            "67.70,  64.32",
            "0.30,   0.29"
    })
    @DisplayName("PIX da 5% de desconto, arredondando meio centavo para cima")
    void pixDaCincoPorCentoDeDesconto(BigDecimal total, BigDecimal aPagarEsperado) {
        assertEquals(aPagarEsperado, Pagamento.PIX.valorAPagar(total));
    }

    @ParameterizedTest(name = "total {0} -> a pagar {1}")
    @CsvSource({
            "100.00, 103.00",
            "120.00, 123.60",
            "0.50,   0.52"
    })
    @DisplayName("CARTAO acrescenta 3%, arredondando meio centavo para cima")
    void cartaoAcrescentaTresPorCento(BigDecimal total, BigDecimal aPagarEsperado) {
        assertEquals(aPagarEsperado, Pagamento.CARTAO.valorAPagar(total));
    }

    @Test
    @DisplayName("DINHEIRO nao altera o valor")
    void dinheiroNaoAlteraOValor() {
        assertEquals(new BigDecimal("40.00"), Pagamento.DINHEIRO.valorAPagar(new BigDecimal("40.00")));
    }

    // Os dois testes abaixo são um PAR DE FRONTEIRA: o último valor aceito e o primeiro recusado.
    // O Main da Aula 09 testava 40,00 e 200,00; nenhum dos dois diz o que acontece em 150,00.
    @Test
    @DisplayName("DINHEIRO aceita exatamente R$ 150,00 (o limite e inclusivo)")
    void dinheiroAceitaExatamenteOLimite() {
        assertEquals(new BigDecimal("150.00"), Pagamento.DINHEIRO.valorAPagar(new BigDecimal("150.00")));
    }

    @Test
    @DisplayName("DINHEIRO recusa um centavo acima de R$ 150,00")
    void dinheiroRecusaAcimaDoLimite() {
        BigDecimal total = new BigDecimal("150.01");

        // assertThrows executa o trecho e FALHA se a exceção esperada não for lançada.
        // Ele devolve a exceção, o que permite verificar também a mensagem.
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> Pagamento.DINHEIRO.valorAPagar(total));

        assertEquals("Dinheiro nao aceito acima de R$ 150.00", erro.getMessage());
    }
}
