package foodexpress.aula09.depois;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FreteExpresso implements CalculoDeFrete {

    private static final BigDecimal TAXA_FIXA = new BigDecimal("15.00");
    private static final BigDecimal ADICIONAL_PERCENTUAL = new BigDecimal("0.02");

    @Override
    public BigDecimal para(BigDecimal subtotal) {
        return TAXA_FIXA.add(subtotal.multiply(ADICIONAL_PERCENTUAL)).setScale(2, RoundingMode.HALF_UP);
    }
}
