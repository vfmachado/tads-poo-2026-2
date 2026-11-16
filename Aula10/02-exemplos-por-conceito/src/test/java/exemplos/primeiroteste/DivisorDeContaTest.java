package exemplos.primeiroteste;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// CONCEITO 1 — O primeiro teste: @Test, assertEquals e as três partes (Arrange, Act, Assert).
//
// Convenções:
//   - a classe de teste fica em src/test/java, no MESMO pacote da classe testada, com o sufixo "Test";
//   - cada método anotado com @Test é um teste independente; não tem parâmetros nem retorno;
//   - o NOME do método descreve o comportamento esperado, não o método chamado
//     ("divisaoExataNaoSobraCentavo", e não "testPorPessoa1").
class DivisorDeContaTest {

    @Test
    void divisaoExataNaoSobraCentavo() {
        // Arrange (preparar): criar o objeto e os dados do cenário
        DivisorDeConta divisor = new DivisorDeConta();

        // Act (agir): UMA chamada ao comportamento que está sendo testado
        int porPessoa = divisor.porPessoa(10000, 4);

        // Assert (verificar): primeiro o valor ESPERADO, depois o OBTIDO
        assertEquals(2500, porPessoa);
    }

    @Test
    void divisaoComRestoArredondaParaCima() {
        DivisorDeConta divisor = new DivisorDeConta();

        int porPessoa = divisor.porPessoa(10000, 3);

        // Aqui o "ou era 3333?" do VerificacaoManual deixa de ser dúvida: a decisão está escrita
        // e é conferida pela máquina a cada execução.
        assertEquals(3334, porPessoa);
    }

    // Para ver uma FALHA: troque 3334 por 3333 acima e rode. O JUnit responde
    //     expected: <3333> but was: <3334>
    // Por isso a ordem (esperado, obtido) importa: invertida, a mensagem mente.
}
