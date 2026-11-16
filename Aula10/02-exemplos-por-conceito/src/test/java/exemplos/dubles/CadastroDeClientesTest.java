package exemplos.dubles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// CONCEITO 6b — Dublê do tipo ESPIÃO (spy): REGISTRA o que o objeto testado mandou fazer.
//
// CadastroDeClientes MANDA o Notificador avisar (um comando, sem retorno). Não há valor devolvido
// para verificar; o único jeito de saber se o aviso saiu é perguntar ao dublê o que ele recebeu.
//
// Regra de bolso (retoma comando x consulta, Aula 04):
//     o objeto testado faz uma CONSULTA à dependência  -> stub  (ver RestauranteTest)
//     o objeto testado dá um COMANDO à dependência     -> espião (este arquivo)
class CadastroDeClientesTest {

    // O dublê inteiro: 8 linhas, sem biblioteca. (Bibliotecas como Mockito geram isto automaticamente;
    // ficam para depois que escrever à mão deixar de ensinar algo.)
    static class NotificadorFalso implements Notificador {
        final List<String> enviados = new ArrayList<>();

        @Override
        public void avisar(String destino, String texto) {
            enviados.add(destino + ": " + texto);
        }
    }

    private NotificadorFalso notificador;
    private CadastroDeClientes cadastro;

    @BeforeEach
    void montarCadastro() {
        notificador = new NotificadorFalso();
        cadastro = new CadastroDeClientes(notificador);
    }

    @Test
    void clienteCadastradoRecebeBoasVindas() {
        cadastro.cadastrar("Ana", "ana@exemplo.com");

        assertEquals(List.of("ana@exemplo.com: Bem-vindo, Ana!"), notificador.enviados);
    }

    // O espião também prova o que NÃO aconteceu.
    @Test
    void emailInvalidoERecusadoENinguemEAvisado() {
        assertThrows(IllegalArgumentException.class, () -> cadastro.cadastrar("Bruno", "bruno.exemplo.com"));

        assertTrue(notificador.enviados.isEmpty());
    }
}
