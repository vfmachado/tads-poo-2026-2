package foodexpress.aula08.depois;

// Null Object: "não há cupom" é um cupom que não concede nada. Evita que
// PedidoDeEntrega precise perguntar "cupom != null?" (R2) e evita null.
public final class SemCupom implements Cupom {

    @Override
    public Dinheiro descontoNoSubtotal(Dinheiro subtotal) {
        return Dinheiro.ZERO;
    }

    @Override
    public Dinheiro descontoNaEntrega(Dinheiro subtotal, Dinheiro taxaBase) {
        return Dinheiro.ZERO;
    }
}
