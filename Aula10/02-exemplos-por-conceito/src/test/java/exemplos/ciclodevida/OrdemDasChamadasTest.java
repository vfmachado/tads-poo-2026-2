package exemplos.ciclodevida;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// CONCEITO 4 (demonstração) — Em que ordem o JUnit chama cada coisa.
//
// Este "teste" não verifica nada: só imprime, para ser projetado em aula. A saída é:
//
//     @BeforeAll   (uma vez, antes de tudo; por isso é static)
//       construtor   <- o JUnit cria um OBJETO NOVO da classe de teste para cada @Test
//       @BeforeEach
//         teste A
//       @AfterEach
//       construtor
//       @BeforeEach
//         teste B
//       @AfterEach
//     @AfterAll    (uma vez, depois de tudo)
//
// Consequência prática: campos de instância da classe de teste NÃO sobrevivem de um teste para outro.
// (Qual teste roda primeiro, A ou B, não é garantido — e nenhum teste deve depender disso.)
class OrdemDasChamadasTest {

    OrdemDasChamadasTest() {
        System.out.println("  construtor");
    }

    @BeforeAll
    static void antesDeTodos() {
        System.out.println("@BeforeAll");
    }

    @BeforeEach
    void antesDeCada() {
        System.out.println("  @BeforeEach");
    }

    @Test
    void testeA() {
        System.out.println("    teste A");
    }

    @Test
    void testeB() {
        System.out.println("    teste B");
    }

    @AfterEach
    void depoisDeCada() {
        System.out.println("  @AfterEach");
    }

    @AfterAll
    static void depoisDeTodos() {
        System.out.println("@AfterAll");
    }
}
