package aula06.seguros;

import java.math.BigDecimal;

public final class CalculoPadrao implements CalculadoraDePremio {

    private static final BigDecimal ALIQUOTA_BASE = new BigDecimal("0.04");

    @Override
    public BigDecimal calcular(PerfilDoCondutor perfil, BigDecimal valorSegurado) {
        return valorSegurado.multiply(ALIQUOTA_BASE);
    }
}
