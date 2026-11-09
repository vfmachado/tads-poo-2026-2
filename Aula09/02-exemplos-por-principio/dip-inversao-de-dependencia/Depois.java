// [DIP] cumprido: a regra de negócio depende de uma ABSTRAÇÃO que ela mesma define (Notificador);
// o detalhe (e-mail, SMS, dublê de teste) depende dessa abstração, e não o contrário.
// A injeção pelo construtor é o MECANISMO; o princípio é a direção da dependência.
import java.util.ArrayList;
import java.util.List;

public class Depois {

    interface Notificador {
        void avisar(String destino, String texto);
    }

    static class NotificadorPorEmail implements Notificador {
        public void avisar(String destino, String texto) {
            System.out.println("[SMTP] " + destino + ": " + texto);
        }
    }

    // Dublê de teste: registra em memória em vez de enviar de verdade.
    static class NotificadorFalso implements Notificador {
        final List<String> enviados = new ArrayList<>();

        public void avisar(String destino, String texto) {
            enviados.add(destino + ": " + texto);
        }
    }

    static class CadastroDeClientes {
        private final Notificador notificador;

        CadastroDeClientes(Notificador notificador) {
            this.notificador = notificador;
        }

        void cadastrar(String nome, String email) {
            // ... regra de negócio ...
            notificador.avisar(email, "Bem-vindo, " + nome + "!");
        }
    }

    public static void main(String[] args) {
        // "Produção": quem monta o sistema escolhe o detalhe.
        new CadastroDeClientes(new NotificadorPorEmail()).cadastrar("Ana", "ana@exemplo.com");

        // "Teste": a mesma regra de negócio, sem e-mail de verdade.
        NotificadorFalso falso = new NotificadorFalso();
        new CadastroDeClientes(falso).cadastrar("Bruno", "bruno@exemplo.com");
        System.out.println("Capturado no teste: " + falso.enviados);
    }
}
