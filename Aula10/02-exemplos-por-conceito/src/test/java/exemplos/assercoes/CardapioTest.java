package exemplos.assercoes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

// CONCEITO 2 — As asserções mais usadas e as três armadilhas de igualdade (objeto, double, BigDecimal).
class CardapioTest {

    private Cardapio cardapioComDoisItens() {
        Cardapio cardapio = new Cardapio();
        cardapio.adicionar("Pizza", "42.00");
        cardapio.adicionar("Suco", "9.50");
        return cardapio;
    }

    @Test
    void assertTrueEAssertFalseParaPerguntasDeSimOuNao() {
        Cardapio cardapio = cardapioComDoisItens();

        assertTrue(cardapio.contem("Pizza"));
        assertFalse(cardapio.contem("Sushi"));
    }

    @Test
    void assertNullEAssertNotNullParaAusenciaEPresenca() {
        Cardapio cardapio = cardapioComDoisItens();

        assertNotNull(cardapio.buscar("Suco"));
        assertNull(cardapio.buscar("Sushi"));
    }

    // assertEquals usa o método equals(). Como Item é um record (Aula 07), dois itens com os mesmos
    // dados são IGUAIS mesmo sendo objetos diferentes. Com uma classe comum sem equals(), este teste
    // falharia — e a falha estaria no modelo, não no teste.
    @Test
    void assertEqualsComparaPorEqualsNaoPorIdentidade() {
        Item buscado = cardapioComDoisItens().buscar("Pizza");
        Item esperado = new Item("Pizza", new BigDecimal("42.00"));

        assertEquals(esperado, buscado);
        assertNotSame(esperado, buscado);   // são dois objetos; iguais, mas não "o mesmo"
    }

    // Listas também se comparam com assertEquals: mesmos elementos, na mesma ordem.
    @Test
    void assertEqualsComparaListasElementoAElemento() {
        assertEquals(List.of("Pizza", "Suco"), cardapioComDoisItens().nomes());
    }

    // ARMADILHA 1 — double: 0.1 + 0.2 não é 0.3 em ponto flutuante.
    // Para double, usa-se SEMPRE a versão de três argumentos, com a tolerância (delta).
    @Test
    void doubleSeComparaComTolerancia() {
        assertNotEquals(0.3, 0.1 + 0.2);            // surpresa: são diferentes
        assertEquals(0.3, 0.1 + 0.2, 0.000001);     // iguais, dentro da tolerância

        assertEquals(25.75, cardapioComDoisItens().precoMedio(), 0.001);
    }

    // ARMADILHA 2 — BigDecimal: equals() considera a ESCALA. 10.0 e 10.00 são o mesmo valor,
    // mas não são "equals". Ou o código garante a escala (setScale), ou o teste compara com compareTo.
    @Test
    void bigDecimalIgualNoValorPodeSerDiferenteNoEquals() {
        BigDecimal umaCasa = new BigDecimal("10.0");
        BigDecimal duasCasas = new BigDecimal("10.00");

        assertNotEquals(umaCasa, duasCasas);              // equals: escalas diferentes
        assertEquals(0, umaCasa.compareTo(duasCasas));    // compareTo: mesmo valor
    }

    // O terceiro argumento é a MENSAGEM que aparece se o teste falhar. Vale a pena quando
    // "expected: <true> but was: <false>" não diria nada a quem lê o relatório.
    @Test
    void mensagemExplicaAFalha() {
        Cardapio cardapio = cardapioComDoisItens();

        assertTrue(cardapio.contem("Suco"), "o item recem-adicionado deveria estar no cardapio");
    }

    // assertAll agrupa verificações do MESMO resultado. Todas são executadas e todas as falhas são
    // relatadas juntas; com três assertEquals soltos, a primeira falha esconderia as outras duas.
    @Test
    void assertAllRelataTodasAsFalhasDeUmaVez() {
        Item pizza = cardapioComDoisItens().buscar("Pizza");

        assertAll("pizza",
                () -> assertEquals("Pizza", pizza.nome()),
                () -> assertEquals(new BigDecimal("42.00"), pizza.preco()));
    }
}
