package foodexpress.aula10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// O recibo é montado À MÃO aqui, sem passar pelo serviço: o que se testa é só a formatação.
// Se o formato mudar, falha este teste e nenhum outro — é o SRP visto pelo lado dos testes.
class ReciboDoPedidoTest {

    private final ReciboDoPedido recibo = new ReciboDoPedido("P-001",
            new BigDecimal("59.70"), new BigDecimal("8.00"), new BigDecimal("67.70"),
            Pagamento.PIX, new BigDecimal("64.32"));

    @Test
    @DisplayName("o texto do recibo traz subtotal, frete, total e valor a pagar")
    void textoTrazTodosOsValores() {
        assertEquals("Pedido P-001 | subtotal R$ 59.70 | frete R$ 8.00 | total R$ 67.70 | PIX: R$ 64.32",
                recibo.texto());
    }

    @Test
    @DisplayName("a mensagem de confirmacao informa o valor a pagar")
    void mensagemDeConfirmacaoInformaOValorAPagar() {
        assertEquals("Pedido P-001 confirmado. Total a pagar: R$ 64.32", recibo.mensagemDeConfirmacao());
    }
}
