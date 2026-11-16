package exemplos.dubles;

// O mesmo CadastroDeClientes do exemplo de DIP da Aula 09, agora com uma regra de validação.
public class CadastroDeClientes {

    private final Notificador notificador;

    public CadastroDeClientes(Notificador notificador) {
        this.notificador = notificador;
    }

    public void cadastrar(String nome, String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("E-mail invalido: " + email);
        }
        notificador.avisar(email, "Bem-vindo, " + nome + "!");
    }
}
