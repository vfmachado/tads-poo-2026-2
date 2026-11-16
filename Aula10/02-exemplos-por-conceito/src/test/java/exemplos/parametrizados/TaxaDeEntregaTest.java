package exemplos.parametrizados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

// CONCEITO 5 — Testes parametrizados e valores-limite.
//
// Uma regra com faixas pede o MESMO teste com MUITOS dados. Em vez de copiar e colar sete métodos
// @Test que só diferem nos números, escreve-se um @ParameterizedTest e uma tabela.
//
// Como escolher os dados (a parte que importa):
//   - PARTIÇÕES DE EQUIVALÊNCIA: a regra tem 3 faixas válidas e 2 inválidas; dentro de uma faixa,
//     qualquer valor se comporta igual, então um representante por faixa basta;
//   - VALORES-LIMITE: os erros se concentram nas fronteiras ("<" no lugar de "<="), então cada
//     fronteira ganha DOIS casos: o último valor de um lado e o primeiro do outro.
class TaxaDeEntregaTest {

    private final TaxaDeEntrega taxa = new TaxaDeEntrega();

    // Cada linha vira um teste independente no relatório, com o nome montado a partir de "name".
    // O JUnit converte os textos da tabela para os tipos dos parâmetros (aqui, int).
    @ParameterizedTest(name = "{0} m -> {1} centavos")
    @CsvSource({
            "0,     500",      // menor distância possível
            "3000,  500",      // último metro da 1ª faixa
            "3001,  900",      // primeiro metro da 2ª faixa
            "8000,  900",
            "8001,  1400",
            "15000, 1400"      // último metro atendido
    })
    void taxaPorFaixaDeDistancia(int metros, int centavosEsperados) {
        assertEquals(centavosEsperados, taxa.paraDistancia(metros));
    }

    // @ValueSource: quando só a ENTRADA varia e o resultado esperado é o mesmo para todas.
    @ParameterizedTest(name = "{0} m e recusado")
    @ValueSource(ints = {-1, 15001, 40000})
    void distanciaInvalidaOuForaDaAreaERecusada(int metros) {
        assertThrows(IllegalArgumentException.class, () -> taxa.paraDistancia(metros));
    }
}
