package foodexpress.aula09.depois;

import java.math.BigDecimal;

public class FreteDeRetirada implements CalculoDeFrete {

    @Override
    public BigDecimal para(BigDecimal subtotal) {
        return BigDecimal.ZERO.setScale(2);
    }
}
