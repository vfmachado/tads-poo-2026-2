package exemplos.excecoes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// CONCEITO 3 — Testar o caminho de ERRO: lançar a exceção certa também é comportamento esperado.
class EstoqueTest {

    @Test
    void retirarDiminuiAsUnidades() {
        Estoque estoque = new Estoque(10);

        estoque.retirar(3);

        assertEquals(7, estoque.unidades());
    }

    // assertThrows recebe o TIPO esperado e um lambda com o trecho que deve lançar.
    // Falha se nada for lançado ou se a exceção for de outro tipo.
    @Test
    void retirarMaisDoQueHaLancaIllegalStateException() {
        Estoque estoque = new Estoque(10);

        assertThrows(IllegalStateException.class, () -> estoque.retirar(11));
    }

    // assertThrows DEVOLVE a exceção: dá para verificar a mensagem (quando ela faz parte do contrato).
    @Test
    void aMensagemInformaPedidoEDisponivel() {
        Estoque estoque = new Estoque(10);

        IllegalStateException erro = assertThrows(IllegalStateException.class, () -> estoque.retirar(11));

        assertEquals("Estoque insuficiente: pedido 11, disponivel 10", erro.getMessage());
    }

    // Depois de um erro, o objeto precisa continuar VÁLIDO: a retirada recusada não pode ter
    // descontado nada. É o teste da invariante, e é o que mais se esquece de escrever.
    @Test
    void retiradaRecusadaNaoAlteraOEstoque() {
        Estoque estoque = new Estoque(10);

        assertThrows(IllegalStateException.class, () -> estoque.retirar(11));

        assertEquals(10, estoque.unidades());
    }

    @Test
    void quantidadeZeroOuNegativaLancaIllegalArgumentException() {
        Estoque estoque = new Estoque(10);

        assertThrows(IllegalArgumentException.class, () -> estoque.retirar(0));
        assertThrows(IllegalArgumentException.class, () -> estoque.retirar(-1));
    }

    // Fronteira: retirar EXATAMENTE o que há é permitido.
    @Test
    void retirarTudoEPermitido() {
        Estoque estoque = new Estoque(10);

        assertDoesNotThrow(() -> estoque.retirar(10));
        assertEquals(0, estoque.unidades());
    }

    // O QUE NÃO FAZER — o mesmo teste escrito com try/catch:
    //
    //     try {
    //         estoque.retirar(11);
    //     } catch (IllegalStateException erro) {
    //         // ok
    //     }
    //
    // Este teste PASSA mesmo que retirar(11) não lance nada: falta o fail() depois da chamada.
    // assertThrows existe para que esse esquecimento seja impossível.
}
