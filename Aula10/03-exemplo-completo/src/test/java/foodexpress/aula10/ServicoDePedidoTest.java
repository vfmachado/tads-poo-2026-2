package foodexpress.aula10;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

// Esta classe responde à mudança M4 da Aula 09: "testar fechar sem enviar e-mail".
// Nenhum teste daqui imprime nem envia nada: o serviço recebe um NotificadorFalso.
class ServicoDePedidoTest {

    private RepositorioDePedidos repositorio;
    private NotificadorFalso notificador;
    private ServicoDePedido servico;

    // Roda ANTES DE CADA teste: todo teste começa com repositório vazio e nenhum aviso registrado.
    // Por isso a ordem de execução dos testes não importa.
    @BeforeEach
    void montarServico() {
        repositorio = new RepositorioDePedidos();
        notificador = new NotificadorFalso();
        servico = new ServicoDePedido(repositorio, notificador);
    }

    @Nested
    @DisplayName("quando o pedido e aceito")
    class QuandoOPedidoEAceito {

        private final Pedido pedido = new Pedido("P-001", "ana@exemplo.com",
                new BigDecimal("59.70"), Frete.ENTREGA, Pagamento.PIX);

        @Test
        @DisplayName("o recibo soma o frete ao subtotal e aplica a forma de pagamento sobre o total")
        void reciboTemFreteTotalEValorAPagar() {
            ReciboDoPedido recibo = servico.fechar(pedido);

            // assertAll executa TODAS as verificações e relata todas as que falharem
            // (sem ele, a primeira falha esconderia as seguintes).
            assertAll(
                    () -> assertEquals(new BigDecimal("8.00"), recibo.frete()),
                    () -> assertEquals(new BigDecimal("67.70"), recibo.total()),
                    () -> assertEquals(new BigDecimal("64.32"), recibo.aPagar()));
        }

        @Test
        @DisplayName("o pedido e gravado")
        void pedidoEGravado() {
            servico.fechar(pedido);

            assertEquals(1, repositorio.quantidade());
        }

        @Test
        @DisplayName("o cliente recebe exatamente um aviso, no seu e-mail, com o valor a pagar")
        void clienteRecebeUmAviso() {
            servico.fechar(pedido);

            assertEquals(
                    List.of(new NotificadorFalso.Aviso("ana@exemplo.com",
                            "Pedido P-001 confirmado. Total a pagar: R$ 64.32")),
                    notificador.avisos());
        }
    }

    @Nested
    @DisplayName("quando a forma de pagamento recusa o total")
    class QuandoOPagamentoRecusa {

        private final Pedido pedido = new Pedido("P-004", "davi@exemplo.com",
                new BigDecimal("200.00"), Frete.RETIRADA, Pagamento.DINHEIRO);

        @Test
        @DisplayName("fechar lanca IllegalArgumentException")
        void fecharLancaExcecao() {
            assertThrows(IllegalArgumentException.class, () -> servico.fechar(pedido));
        }

        // Depois de um erro, verifica-se também o que NÃO aconteceu. O Main só mostrava a mensagem
        // "PEDIDO RECUSADO"; se o pedido tivesse sido gravado antes da recusa, ninguém veria.
        @Test
        @DisplayName("o pedido nao e gravado e ninguem e avisado")
        void nadaEGravadoNemAvisado() {
            assertThrows(IllegalArgumentException.class, () -> servico.fechar(pedido));

            assertAll(
                    () -> assertEquals(0, repositorio.quantidade()),
                    () -> assertTrue(notificador.avisos().isEmpty()));
        }

        // Um cenário que o Main não tinha: o subtotal (R$ 135,00) está ABAIXO do limite do dinheiro,
        // mas o total com frete expresso (R$ 152,70) está acima. Este teste documenta de qual dos dois
        // valores o limite depende.
        @Test
        @DisplayName("o limite do dinheiro vale para o total com frete, nao para o subtotal")
        void limiteDoDinheiroValeParaOTotalComFrete() {
            Pedido expresso = new Pedido("P-006", "fabio@exemplo.com",
                    new BigDecimal("135.00"), Frete.EXPRESSA, Pagamento.DINHEIRO);

            assertThrows(IllegalArgumentException.class, () -> servico.fechar(expresso));
        }
    }

    @Nested
    @DisplayName("quando o envio do aviso falha")
    class QuandoOAvisoFalha {

        // TESTE DE CARACTERIZAÇÃO: registra o que o código FAZ hoje, não o que ele deveria fazer.
        // É o "acoplamento temporal" apontado no gabarito da Aula 09: salvar acontece antes de avisar,
        // e não há transação. Se a regra de negócio mudar ("sem aviso, sem pedido"), é este teste que
        // deve ser reescrito PRIMEIRO, e ele vai falhar até o serviço ser corrigido.
        @Test
        @DisplayName("o pedido ja ficou gravado (comportamento atual, registrado para discussao)")
        void pedidoFicaGravadoMesmoSemAviso() {
            // Segundo dublê, escrito como lambda: Notificador tem um método só (interface funcional).
            Notificador notificadorForaDoAr = (destino, mensagem) -> {
                throw new IllegalStateException("servidor de e-mail fora do ar");
            };
            ServicoDePedido servicoSemEmail = new ServicoDePedido(repositorio, notificadorForaDoAr);
            Pedido pedido = new Pedido("P-007", "gabi@exemplo.com",
                    new BigDecimal("40.00"), Frete.RETIRADA, Pagamento.DINHEIRO);

            assertThrows(IllegalStateException.class, () -> servicoSemEmail.fechar(pedido));

            assertEquals(1, repositorio.quantidade());
        }
    }
}
