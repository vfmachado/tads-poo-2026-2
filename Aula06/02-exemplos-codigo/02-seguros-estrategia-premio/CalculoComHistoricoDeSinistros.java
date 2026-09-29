package aula06.seguros;

import java.math.BigDecimal;

/**
 * Agrava o prêmio proporcionalmente ao histórico recente de sinistros,
 * mantendo a aliquota-base padrão como piso.
 */
public final class CalculoComHistoricoDeSinistros implements CalculadoraDePremio {

    private static final BigDecimal ALIQUOTA_BASE = new BigDecimal("0.04");
    private static final BigDecimal AGRAVO_POR_SINISTRO = new BigDecimal("0.015");

    @Override
    public BigDecimal calcular(PerfilDoCondutor perfil, BigDecimal valorSegurado) {
        BigDecimal aliquota = ALIQUOTA_BASE
                .add(AGRAVO_POR_SINISTRO.multiply(BigDecimal.valueOf(perfil.sinistrosUltimosDoisAnos())));
        return valorSegurado.multiply(aliquota);
    }
}
