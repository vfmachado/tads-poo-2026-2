package foodexpress.aula09.depois;

import java.math.BigDecimal;

public class PagamentoEmDinheiro implements FormaDePagamento {

    private static final BigDecimal LIMITE = new BigDecimal("150.00");

    @Override
    public String nome() {
        return "DINHEIRO";
    }

    @Override
    public BigDecimal valorAPagar(BigDecimal total) {
        if (total.compareTo(LIMITE) > 0) {
            throw new IllegalArgumentException("Dinheiro nao aceito acima de R$ " + LIMITE.toPlainString());
        }
        return total;
    }
}
