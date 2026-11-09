package foodexpress.aula09.equilibrado;

import java.math.BigDecimal;

public record ReciboDoPedido(String idDoPedido, BigDecimal subtotal, BigDecimal frete,
                             BigDecimal total, Pagamento pagamento, BigDecimal aPagar) {

    public String texto() {
        return "Pedido " + idDoPedido
                + " | subtotal R$ " + subtotal.toPlainString()
                + " | frete R$ " + frete.toPlainString()
                + " | total R$ " + total.toPlainString()
                + " | " + pagamento + ": R$ " + aPagar.toPlainString();
    }

    public String mensagemDeConfirmacao() {
        return "Pedido " + idDoPedido + " confirmado. Total a pagar: R$ " + aPagar.toPlainString();
    }
}
