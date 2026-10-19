// E-commerce: descobrir a cidade de entrega de um pedido.
// [R5] violada: o chamador conhece Pedido -> Cliente -> Endereco -> cidade.
public class Antes {

    static class Endereco {
        private final String cidade;
        Endereco(String cidade) { this.cidade = cidade; }
        String getCidade() { return cidade; }
    }

    static class Cliente {
        private final Endereco endereco;
        Cliente(Endereco endereco) { this.endereco = endereco; }
        Endereco getEndereco() { return endereco; }
    }

    static class Pedido {
        private final Cliente cliente;
        Pedido(Cliente cliente) { this.cliente = cliente; }
        Cliente getCliente() { return cliente; }
    }

    public static void main(String[] args) {
        Pedido pedido = new Pedido(new Cliente(new Endereco("Bento Goncalves")));
        System.out.println(pedido.getCliente().getEndereco().getCidade());
    }
}
