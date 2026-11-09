package foodexpress.aula09.depois;

import java.math.BigDecimal;

public class FreteDeEntrega implements CalculoDeFrete {

    private static final BigDecimal LIMITE_FRETE_GRATIS = new BigDecimal("100.00");
    private static final BigDecimal TAXA_FIXA = new BigDecimal("8.00");

    @Override
    public BigDecimal para(BigDecimal subtotal) {
        if (subtotal.compareTo(LIMITE_FRETE_GRATIS) >= 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        return TAXA_FIXA;
    }
}
