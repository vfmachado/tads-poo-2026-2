package foodexpress.aula08.equilibrado;

import java.util.List;

// Coleção de primeira classe mantida (R4): tem invariante própria
// ("não vazia") e comportamento ("subtotal").
public final class ItensDoPedido {

    private final List<ItemDoPedido> itens;

    public ItensDoPedido(List<ItemDoPedido> itens) {
        if (itens.isEmpty()) {
            throw new IllegalArgumentException("O pedido precisa ter ao menos um item.");
        }
        this.itens = List.copyOf(itens);
    }

    public Dinheiro subtotal() {
        return itens.stream()
                .map(ItemDoPedido::subtotal)
                .reduce(Dinheiro.ZERO, Dinheiro::mais);
    }
}
