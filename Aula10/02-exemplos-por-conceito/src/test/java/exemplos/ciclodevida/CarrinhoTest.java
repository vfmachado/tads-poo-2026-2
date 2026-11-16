package exemplos.ciclodevida;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

// CONCEITO 4 — Ciclo de vida: @BeforeEach, isolamento entre testes, @DisplayName e @Nested.
@DisplayName("Carrinho")
class CarrinhoTest {

    private Carrinho carrinho;

    // Roda antes de CADA teste. Todo teste recebe um carrinho novo: o que um teste fizer
    // não é visto pelo seguinte, e a ordem em que rodam deixa de importar.
    @BeforeEach
    void criarCarrinho() {
        carrinho = new Carrinho();
    }

    @Test
    @DisplayName("comeca vazio")
    void comecaVazio() {
        assertEquals(0, carrinho.quantidadeDeItens());
        assertEquals(0, carrinho.totalEmCentavos());
    }

    // PROVA DO ISOLAMENTO: os dois testes abaixo adicionam um item e esperam encontrar UM item.
    // Se o carrinho fosse compartilhado, o segundo a rodar encontraria dois.
    @Test
    @DisplayName("um item adicionado: tem 1 item (primeiro teste)")
    void umItemAdicionadoPrimeiro() {
        carrinho.adicionar(4200);

        assertEquals(1, carrinho.quantidadeDeItens());
    }

    @Test
    @DisplayName("um item adicionado: tem 1 item (segundo teste, mesmo resultado)")
    void umItemAdicionadoSegundo() {
        carrinho.adicionar(950);

        assertEquals(1, carrinho.quantidadeDeItens());
    }

    // @Nested agrupa testes que partem do MESMO cenário. O @BeforeEach de fora roda primeiro
    // (cria o carrinho) e o de dentro roda depois (coloca os itens).
    @Nested
    @DisplayName("com pizza e suco")
    class ComPizzaESuco {

        @BeforeEach
        void adicionarPizzaESuco() {
            carrinho.adicionar(4200);
            carrinho.adicionar(950);
        }

        @Test
        @DisplayName("o total e a soma dos precos")
        void totalESomaDosPrecos() {
            assertEquals(5150, carrinho.totalEmCentavos());
        }

        @Test
        @DisplayName("esvaziar remove tudo")
        void esvaziarRemoveTudo() {
            carrinho.esvaziar();

            assertEquals(0, carrinho.quantidadeDeItens());
        }
    }
}
