package foodexpress.aula09.antes;

import java.math.BigDecimal;
import java.util.function.Supplier;

// Cenários de caracterização: depois/ e equilibrado/ precisam produzir exatamente esta saída.
public class Main {

    public static void main(String[] args) {
        ServicoDePedido servico = new ServicoDePedido();

        executar("A) entrega, R$ 59,70, PIX",
                () -> servico.fechar(new Pedido("P-001", "ana@exemplo.com", new BigDecimal("59.70"), "ENTREGA", "PIX")));

        executar("B) entrega com frete gratis, R$ 120,00, cartao",
                () -> servico.fechar(new Pedido("P-002", "bruno@exemplo.com", new BigDecimal("120.00"), "ENTREGA", "CARTAO")));

        executar("C) expressa, R$ 80,00, PIX",
                () -> servico.fechar(new Pedido("P-003", "carla@exemplo.com", new BigDecimal("80.00"), "EXPRESSA", "PIX")));

        executar("D) retirada, R$ 200,00, dinheiro (acima do limite)",
                () -> servico.fechar(new Pedido("P-004", "davi@exemplo.com", new BigDecimal("200.00"), "RETIRADA", "DINHEIRO")));

        executar("E) retirada, R$ 40,00, dinheiro",
                () -> servico.fechar(new Pedido("P-005", "eva@exemplo.com", new BigDecimal("40.00"), "RETIRADA", "DINHEIRO")));

        System.out.println("Pedidos gravados: " + servico.quantidadeGravada());
    }

    private static void executar(String titulo, Supplier<String> cenario) {
        System.out.println("=== " + titulo + " ===");
        try {
            System.out.println(cenario.get());
        } catch (IllegalArgumentException erro) {
            System.out.println("PEDIDO RECUSADO: " + erro.getMessage());
        }
    }
}
