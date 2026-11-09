package foodexpress.aula09.depois;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PagamentoCartao implements FormaDePagamento {

    private static final BigDecimal FATOR_TAXA = new BigDecimal("1.03");

    @Override
    public String nome() {
        return "CARTAO";
    }

    @Override
    public BigDecimal valorAPagar(BigDecimal total) {
        return total.multiply(FATOR_TAXA).setScale(2, RoundingMode.HALF_UP);
    }
}
