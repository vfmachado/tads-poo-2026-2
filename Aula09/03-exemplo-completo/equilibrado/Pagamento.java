package foodexpress.aula09.equilibrado;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Mesma decisão de Frete: enum no lugar de interface + 3 classes. O contrato (LSP) é o mesmo:
// devolve o valor a pagar (escala 2) ou lança IllegalArgumentException se não aceita o total.
public enum Pagamento {

    PIX {
        @Override
        public BigDecimal valorAPagar(BigDecimal total) {
            return total.multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.HALF_UP);
        }
    },
    CARTAO {
        @Override
        public BigDecimal valorAPagar(BigDecimal total) {
            return total.multiply(new BigDecimal("1.03")).setScale(2, RoundingMode.HALF_UP);
        }
    },
    DINHEIRO {
        @Override
        public BigDecimal valorAPagar(BigDecimal total) {
            BigDecimal limite = new BigDecimal("150.00");
            if (total.compareTo(limite) > 0) {
                throw new IllegalArgumentException("Dinheiro nao aceito acima de R$ " + limite.toPlainString());
            }
            return total;
        }
    };

    public abstract BigDecimal valorAPagar(BigDecimal total);
}
