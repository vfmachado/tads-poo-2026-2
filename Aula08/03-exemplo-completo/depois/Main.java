package foodexpress.aula08.depois;

import java.util.List;
import java.util.function.Supplier;

// Mesmos cenários de antes/Main.java; a saída precisa ser idêntica.
public class Main {

    public static void main(String[] args) {
        executar("A) VALE10, PIX, 2,5 km", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(
                        item("29.90", 3),
                        item("19.80", 1))),
                new Entrega(new DistanciaEmKm(2.5), Prioridade.NORMAL),
                CatalogoDeCupons.para(new CodigoDeCupom("VALE10")),
                FormaDePagamento.PIX).fechar());

        executar("B) FRETEGRATIS + prioridade, cartao, 6 km", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(item("65.00", 1))),
                new Entrega(new DistanciaEmKm(6.0), Prioridade.PRIORITARIA),
                CatalogoDeCupons.para(new CodigoDeCupom("FRETEGRATIS")),
                FormaDePagamento.CARTAO).fechar());

        executar("C) VALE10 abaixo do subtotal minimo", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(item("29.90", 3))),
                new Entrega(new DistanciaEmKm(2.5), Prioridade.NORMAL),
                CatalogoDeCupons.para(new CodigoDeCupom("VALE10")),
                FormaDePagamento.PIX).fechar());

        executar("D) dinheiro acima do limite", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(item("200.00", 1))),
                new Entrega(new DistanciaEmKm(1.0), Prioridade.NORMAL),
                CatalogoDeCupons.para(CodigoDeCupom.NENHUM),
                FormaDePagamento.DINHEIRO).fechar());

        executar("E) fora da area de entrega", () -> new PedidoDeEntrega(
                new ItensDoPedido(List.of(item("29.90", 1))),
                new Entrega(new DistanciaEmKm(12.0), Prioridade.NORMAL),
                CatalogoDeCupons.para(CodigoDeCupom.NENHUM),
                FormaDePagamento.PIX).fechar());
    }

    private static ItemDoPedido item(String precoUnitario, int unidades) {
        return new ItemDoPedido(Dinheiro.reais(precoUnitario), new Quantidade(unidades));
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
