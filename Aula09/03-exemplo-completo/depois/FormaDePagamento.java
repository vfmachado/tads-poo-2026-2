package foodexpress.aula09.depois;

import java.math.BigDecimal;

// [O] Ponto de extensão. [L] Contrato: devolve o valor a pagar (escala 2) ou lança
// IllegalArgumentException se a forma não aceita o total. Toda implementação cumpre exatamente isso.
public interface FormaDePagamento {

    String nome();

    BigDecimal valorAPagar(BigDecimal total);
}
