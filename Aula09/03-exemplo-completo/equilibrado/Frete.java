package foodexpress.aula09.equilibrado;

import java.math.BigDecimal;
import java.math.RoundingMode;

// OCP flexibilizado: enum com método abstrato por constante. Novo tipo de atendimento = nova constante
// (EDITA este arquivo, mas só ele, e sem if encadeado). Se os tipos passarem a ser cadastrados em
// tempo de execução ou por módulos de terceiros, migrar para interface (ver depois/CalculoDeFrete).
public enum Frete {

    ENTREGA {
        @Override
        public BigDecimal para(BigDecimal subtotal) {
            if (subtotal.compareTo(new BigDecimal("100.00")) >= 0) {
                return BigDecimal.ZERO.setScale(2);
            }
            return new BigDecimal("8.00");
        }
    },
    EXPRESSA {
        @Override
        public BigDecimal para(BigDecimal subtotal) {
            return new BigDecimal("15.00").add(subtotal.multiply(new BigDecimal("0.02")))
                    .setScale(2, RoundingMode.HALF_UP);
        }
    },
    RETIRADA {
        @Override
        public BigDecimal para(BigDecimal subtotal) {
            return BigDecimal.ZERO.setScale(2);
        }
    };

    public abstract BigDecimal para(BigDecimal subtotal);
}
