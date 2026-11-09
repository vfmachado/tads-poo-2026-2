// Cadastro de clientes que envia e-mail de boas-vindas.
// [DIP] violado: a regra de negócio (alto nível) cria e depende do detalhe (envio de e-mail, baixo nível).
public class Antes {

    static class EnviadorDeEmail {
        void enviar(String destino, String texto) {
            // Na vida real: abre conexão SMTP, autentica, envia. Aqui, só imprime.
            System.out.println("[SMTP] " + destino + ": " + texto);
        }
    }

    static class CadastroDeClientes {
        private final EnviadorDeEmail enviador = new EnviadorDeEmail(); // [DIP] new de um detalhe concreto

        void cadastrar(String nome, String email) {
            // ... regra de negócio ...
            enviador.enviar(email, "Bem-vindo, " + nome + "!");
        }
    }

    public static void main(String[] args) {
        new CadastroDeClientes().cadastrar("Ana", "ana@exemplo.com");
        // Como testar "cadastrar" sem mandar e-mail de verdade? Como trocar por SMS? Não dá sem editar a classe.
    }
}
