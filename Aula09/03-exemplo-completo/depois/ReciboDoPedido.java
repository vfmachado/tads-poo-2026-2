package foodexpress.aula09.depois;

import java.math.BigDecimal;

// [S] Única razão para mudar: o formato do texto.
public record ReciboDoPedido(String idDoPedido, BigDecimal subtotal, BigDecimal frete,
                             BigDecimal total, String formaDePagamento, BigDecimal aPagar) {

    public String texto() {
        return "Pedido " + idDoPedido
                + " | subtotal R$ " + subtotal.toPlainString()
                + " | frete R$ " + frete.toPlainString()
                + " | total R$ " + total.toPlainString()
                + " | " + formaDePagamento + ": R$ " + aPagar.toPlainString();
    }

    public String mensagemDeConfirmacao() {
        return "Pedido " + idDoPedido + " confirmado. Total a pagar: R$ " + aPagar.toPlainString();
    }
}
