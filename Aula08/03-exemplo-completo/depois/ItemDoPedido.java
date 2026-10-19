package foodexpress.aula08.depois;

// [R8] Duas variáveis de instância. (O nome do produto foi omitido de
// propósito: não participa de nenhuma regra desta aula.)
public final class ItemDoPedido {

    private final Dinheiro precoUnitario;
    private final Quantidade quantidade;

    public ItemDoPedido(Dinheiro precoUnitario, Quantidade quantidade) {
        if (precoUnitario.menorQue(Dinheiro.ZERO)) {
            throw new IllegalArgumentException("Valor unitario nao pode ser negativo.");
        }
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public Dinheiro subtotal() {
        return quantidade.valorTotalDe(precoUnitario);
    }
}
