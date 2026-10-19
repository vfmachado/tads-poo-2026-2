package foodexpress.aula08.antes;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Código INTENCIONALMENTE ruim para a Aula 08: viola as nove regras de
// Object Calisthenics ao mesmo tempo. Cada violação está marcada com [Rn].
public class FechamentoPedido {

    public String fechar(PedidoEntrega p) {
        BigDecimal sub = BigDecimal.ZERO;                                  // [R3] dinheiro como BigDecimal "solto"
        if (p.getItens() != null && p.getItens().size() > 0) {             // [R4] coleção crua
            for (ItemPedido i : p.getItens()) {                            // [R1] nível 2
                if (i.getQt() > 0) {                                       // [R1] nível 3 — [R9] getters
                    if (i.getVl().compareTo(BigDecimal.ZERO) >= 0) {       // [R1] nível 4 — [R5] i.getVl().compareTo
                        sub = sub.add(i.getVl().multiply(BigDecimal.valueOf(i.getQt())));
                    } else {                                               // [R2]
                        throw new IllegalArgumentException("Valor unitario nao pode ser negativo.");
                    }
                } else {                                                   // [R2]
                    throw new IllegalArgumentException("Quantidade deve ser positiva.");
                }
            }
        } else {                                                           // [R2]
            throw new IllegalArgumentException("O pedido precisa ter ao menos um item.");
        }

        double d = p.getEnd().getBairro().getDistKm();                     // [R5] três pontos — [R6] "d"
        BigDecimal tx;                                                     // [R6] "tx"
        if (d < 0) {
            throw new IllegalArgumentException("Distancia nao pode ser negativa.");
        } else if (d <= 3) {                                               // [R2]
            tx = new BigDecimal("5.00");
        } else if (d <= 8) {
            tx = new BigDecimal("9.00");
        } else {
            throw new IllegalArgumentException("Endereco fora da area de entrega (mais de 8 km).");
        }

        BigDecimal desc = BigDecimal.ZERO;                                 // [R6]
        String c = p.getCp();
        if (c != null && !c.isBlank()) {
            if (c.equals("VALE10")) {                                      // [R3] código do cupom como String
                if (sub.compareTo(new BigDecimal("100.00")) >= 0) {        // [R1] nível 3
                    desc = sub.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);
                } else {
                    throw new IllegalArgumentException("Cupom VALE10 exige subtotal minimo de R$ 100.00");
                }
            } else if (c.equals("FRETEGRATIS")) {
                if (sub.compareTo(new BigDecimal("60.00")) >= 0) {
                    tx = BigDecimal.ZERO;
                } else {
                    throw new IllegalArgumentException("Cupom FRETEGRATIS exige subtotal minimo de R$ 60.00");
                }
            } else {
                throw new IllegalArgumentException("Cupom desconhecido: " + c);
            }
        }
        if (p.isPri()) {
            tx = tx.add(new BigDecimal("6.00"));
        }

        BigDecimal tot = sub.subtract(desc).add(tx);
        String pg = p.getPgto();                                           // [R3] forma de pagamento como String
        if (pg.equals("DINHEIRO")) {
            if (tot.compareTo(new BigDecimal("150.00")) > 0) {
                throw new IllegalArgumentException(
                        "Pagamento em dinheiro nao e aceito para pedidos acima de R$ 150.00");
            }
        } else if (!pg.equals("PIX") && !pg.equals("CARTAO")) {
            throw new IllegalArgumentException("Forma de pagamento desconhecida: " + pg);
        }

        return "Subtotal: R$ " + sub.setScale(2, RoundingMode.HALF_UP) + "\n"
                + "Desconto: R$ " + desc.setScale(2, RoundingMode.HALF_UP) + "\n"
                + "Entrega: R$ " + tx.setScale(2, RoundingMode.HALF_UP) + "\n"
                + "TOTAL: R$ " + tot.setScale(2, RoundingMode.HALF_UP);
    }
}
