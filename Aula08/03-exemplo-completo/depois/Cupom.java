package foodexpress.aula08.depois;

// [R2] Contrato que substitui a cadeia "if (c.equals("VALE10")) ... else if
// (c.equals("FRETEGRATIS")) ... else ..." de FechamentoPedido.
public interface Cupom {

    Dinheiro descontoNoSubtotal(Dinheiro subtotal);

    Dinheiro descontoNaEntrega(Dinheiro subtotal, Dinheiro taxaBase);
}
