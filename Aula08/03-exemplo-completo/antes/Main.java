package foodexpress.aula08.antes;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {
        FechamentoPedido fechamento = new FechamentoPedido();

        executar("A) VALE10, PIX, 2,5 km", () -> fechamento.fechar(new PedidoEntrega(
                List.of(new ItemPedido("Hamburguer Classico", new BigDecimal("29.90"), 3),
                        new ItemPedido("Batata Frita 200g", new BigDecimal("19.80"), 1)),
                new Endereco("Rua das Flores, 10", new Bairro("Centro", 2.5)),
                "VALE10", "PIX", false)));

        executar("B) FRETEGRATIS + prioridade, cartao, 6 km", () -> fechamento.fechar(new PedidoEntrega(
                List.of(new ItemPedido("Combo Familia", new BigDecimal("65.00"), 1)),
                new Endereco("Av. Brasil, 500", new Bairro("Industrial", 6.0)),
                "FRETEGRATIS", "CARTAO", true)));

        executar("C) VALE10 abaixo do subtotal minimo", () -> fechamento.fechar(new PedidoEntrega(
                List.of(new ItemPedido("Hamburguer Classico", new BigDecimal("29.90"), 3)),
                new Endereco("Rua das Flores, 10", new Bairro("Centro", 2.5)),
                "VALE10", "PIX", false)));

        executar("D) dinheiro acima do limite", () -> fechamento.fechar(new PedidoEntrega(
                List.of(new ItemPedido("Banquete", new BigDecimal("200.00"), 1)),
                new Endereco("Rua das Flores, 10", new Bairro("Centro", 1.0)),
                null, "DINHEIRO", false)));

        executar("E) fora da area de entrega", () -> fechamento.fechar(new PedidoEntrega(
                List.of(new ItemPedido("Hamburguer Classico", new BigDecimal("29.90"), 1)),
                new Endereco("Estrada Velha, km 12", new Bairro("Rural", 12.0)),
                null, "PIX", false)));
    }

    private static void executar(String titulo, Supplier<String> cenario) {
        System.out.println("=== " + titulo + " ===");
        try {
            System.out.println(cenario.get());
        } catch (IllegalArgumentException erro) {
            System.out.println("PEDIDO RECUSADO: " + erro.getMessage());
        }
        System.out.println();
    }
}
