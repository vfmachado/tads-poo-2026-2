package foodexpress.aula08.depois;

import java.util.List;

// [R4] Coleção de primeira classe: a lista de itens e as regras que
// dependem dela ("não pode ser vazia", "subtotal") vivem juntas, e a lista
// nunca escapa (cópia defensiva no construtor, nenhum getter).
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
