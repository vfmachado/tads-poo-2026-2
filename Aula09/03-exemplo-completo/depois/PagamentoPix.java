package foodexpress.aula09.depois;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PagamentoPix implements FormaDePagamento {

    private static final BigDecimal FATOR_DESCONTO = new BigDecimal("0.95");

    @Override
    public String nome() {
        return "PIX";
    }

    @Override
    public BigDecimal valorAPagar(BigDecimal total) {
        return total.multiply(FATOR_DESCONTO).setScale(2, RoundingMode.HALF_UP);
    }
}
