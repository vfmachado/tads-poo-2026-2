package foodexpress.aula09.antes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// [S] Cinco razões para mudar na mesma classe: regra de frete, regra de pagamento, persistência,
//     notificação e formatação do recibo.
public class ServicoDePedido {

    // [D] Detalhes concretos criados aqui dentro: não dá para trocar nem para testar sem e-mail de verdade.
    private final Notificador notificador = new NotificadorPorEmail();
    private final List<Pedido> pedidosGravados = new ArrayList<>();

    public String fechar(Pedido pedido) {
        // [O] Cada novo tipo de atendimento exige EDITAR este método (e reabrir os testes dele).
        BigDecimal frete;
        if (pedido.tipo().equals("ENTREGA")) {
            if (pedido.subtotal().compareTo(new BigDecimal("100.00")) >= 0) {
                frete = BigDecimal.ZERO;
            } else {
                frete = new BigDecimal("8.00");
            }
        } else if (pedido.tipo().equals("EXPRESSA")) {
            frete = new BigDecimal("15.00").add(pedido.subtotal().multiply(new BigDecimal("0.02")));
        } else if (pedido.tipo().equals("RETIRADA")) {
            frete = BigDecimal.ZERO;
        } else {
            throw new IllegalArgumentException("Tipo de atendimento desconhecido: " + pedido.tipo());
        }
        frete = frete.setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = pedido.subtotal().add(frete);

        // [O] Idem para cada nova forma de pagamento.
        BigDecimal aPagar;
        if (pedido.forma().equals("PIX")) {
            aPagar = total.multiply(new BigDecimal("0.95"));
        } else if (pedido.forma().equals("CARTAO")) {
            aPagar = total.multiply(new BigDecimal("1.03"));
        } else if (pedido.forma().equals("DINHEIRO")) {
            if (total.compareTo(new BigDecimal("150.00")) > 0) {
                throw new IllegalArgumentException("Dinheiro nao aceito acima de R$ 150.00");
            }
            aPagar = total;
        } else {
            throw new IllegalArgumentException("Forma de pagamento desconhecida: " + pedido.forma());
        }
        aPagar = aPagar.setScale(2, RoundingMode.HALF_UP);

        pedidosGravados.add(pedido); // [S] persistência misturada com regra de negócio

        notificador.enviarEmail(pedido.emailDoCliente(),
                "Pedido " + pedido.id() + " confirmado. Total a pagar: R$ " + aPagar.toPlainString());

        return "Pedido " + pedido.id()
                + " | subtotal R$ " + pedido.subtotal().toPlainString()
                + " | frete R$ " + frete.toPlainString()
                + " | total R$ " + total.toPlainString()
                + " | " + pedido.forma() + ": R$ " + aPagar.toPlainString();
    }

    public int quantidadeGravada() {
        return pedidosGravados.size();
    }
}
