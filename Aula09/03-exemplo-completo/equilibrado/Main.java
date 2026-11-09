package foodexpress.aula09.equilibrado;

import java.math.BigDecimal;
import java.util.function.Supplier;

// Mesmos cenários de antes/Main.java e depois/Main.java; a saída precisa ser idêntica.
public class Main {

    public static void main(String[] args) {
        RepositorioDePedidos repositorio = new RepositorioDePedidos();
        ServicoDePedido servico = new ServicoDePedido(repositorio, new NotificadorPorEmail());

        executar("A) entrega, R$ 59,70, PIX", () -> servico.fechar(
                new Pedido("P-001", "ana@exemplo.com", new BigDecimal("59.70"), Frete.ENTREGA, Pagamento.PIX)));

        executar("B) entrega com frete gratis, R$ 120,00, cartao", () -> servico.fechar(
                new Pedido("P-002", "bruno@exemplo.com", new BigDecimal("120.00"), Frete.ENTREGA, Pagamento.CARTAO)));

        executar("C) expressa, R$ 80,00, PIX", () -> servico.fechar(
                new Pedido("P-003", "carla@exemplo.com", new BigDecimal("80.00"), Frete.EXPRESSA, Pagamento.PIX)));

        executar("D) retirada, R$ 200,00, dinheiro (acima do limite)", () -> servico.fechar(
                new Pedido("P-004", "davi@exemplo.com", new BigDecimal("200.00"), Frete.RETIRADA, Pagamento.DINHEIRO)));

        executar("E) retirada, R$ 40,00, dinheiro", () -> servico.fechar(
                new Pedido("P-005", "eva@exemplo.com", new BigDecimal("40.00"), Frete.RETIRADA, Pagamento.DINHEIRO)));

        System.out.println("Pedidos gravados: " + repositorio.quantidade());
    }

    private static void executar(String titulo, Supplier<ReciboDoPedido> cenario) {
        System.out.println("=== " + titulo + " ===");
        try {
            System.out.println(cenario.get().texto());
        } catch (IllegalArgumentException erro) {
            System.out.println("PEDIDO RECUSADO: " + erro.getMessage());
        }
    }
}
