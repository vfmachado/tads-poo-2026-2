// [R5] cumprida: cada objeto fala só com o colaborador direto.
// Se Endereco mudar, só Cliente é afetado; Pedido e o chamador não sabem.
// Atenção: isto NÃO é "quebrar a cadeia em duas linhas" — a cadeia deixou de existir fora.
public class Depois {

    static class Endereco {
        private final String cidade;
        Endereco(String cidade) { this.cidade = cidade; }
        String cidade() { return cidade; }
    }

    static class Cliente {
        private final Endereco endereco;
        Cliente(Endereco endereco) { this.endereco = endereco; }
        String cidadeDeEntrega() { return endereco.cidade(); }
    }

    static class Pedido {
        private final Cliente cliente;
        Pedido(Cliente cliente) { this.cliente = cliente; }
        String cidadeDeEntrega() { return cliente.cidadeDeEntrega(); }
    }

    public static void main(String[] args) {
        Pedido pedido = new Pedido(new Cliente(new Endereco("Bento Goncalves")));
        System.out.println(pedido.cidadeDeEntrega());
    }
}
