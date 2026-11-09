package foodexpress.aula09.depois;

import java.math.BigDecimal;

// [O] Ponto de extensão: novo tipo de atendimento = nova classe que implementa esta interface.
public interface CalculoDeFrete {

    BigDecimal para(BigDecimal subtotal);
}
