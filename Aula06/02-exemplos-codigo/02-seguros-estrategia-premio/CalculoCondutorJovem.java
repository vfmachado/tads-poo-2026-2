package aula06.seguros;

import java.math.BigDecimal;

/**
 * Estatisticamente, condutores com menos de 25 anos têm maior sinistralidade
 * — a política comercial define uma aliquota-base maior para este perfil.
 */
public final class CalculoCondutorJovem implements CalculadoraDePremio {

    private static final int IDADE_LIMITE = 25;
    private static final BigDecimal ALIQUOTA_JOVEM = new BigDecimal("0.07");

    @Override
    public BigDecimal calcular(PerfilDoCondutor perfil, BigDecimal valorSegurado) {
        if (perfil.idade() >= IDADE_LIMITE) {
            throw new IllegalArgumentException(
                    "CalculoCondutorJovem só se aplica a condutores com menos de " + IDADE_LIMITE + " anos.");
        }
        return valorSegurado.multiply(ALIQUOTA_JOVEM);
    }
}
