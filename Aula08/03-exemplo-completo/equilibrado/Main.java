package foodexpress.aula08.equilibrado;

import java.util.List;
import java.util.function.Supplier;

// Mesmos cenários de antes/Main.java e depois/Main.java; a saída precisa ser idêntica.
public class Main {

    public static void main(String[] args) {
        executar("A) VALE10, PIX, 2,5 km", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(item("29.90", 3), item("19.80", 1))),
                2.5, false, Cupom.pelo("VALE10"), FormaDePagamento.PIX).fechar());

        executar("B) FRETEGRATIS + prioridade, cartao, 6 km", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(item("65.00", 1))),
                6.0, true, Cupom.pelo("FRETEGRATIS"), FormaDePagamento.CARTAO).fechar());

        executar("C) VALE10 abaixo do subtotal minimo", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(item("29.90", 3))),
                2.5, false, Cupom.pelo("VALE10"), FormaDePagamento.PIX).fechar());

        executar("D) dinheiro acima do limite", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(item("200.00", 1))),
                1.0, false, Cupom.pelo(null), FormaDePagamento.DINHEIRO).fechar());

        executar("E) fora da area de entrega", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(item("29.90", 1))),
                12.0, false, Cupom.pelo(null), FormaDePagamento.PIX).fechar());
    }

    private static ItemDoPedido item(String precoUnitario, int unidades) {
        return new ItemDoPedido(Dinheiro.reais(precoUnitario), unidades);
    }

    private static void executar(String titulo, Supplier<ReciboDoPedido> cenario) {
        System.out.println("=== " + titulo + " ===");
        try {
            System.out.println(cenario.get().texto());
        } catch (IllegalArgumentException erro) {
            System.out.println("PEDIDO RECUSADO: " + erro.getMessage());
        }
        System.out.println();
    }
}
