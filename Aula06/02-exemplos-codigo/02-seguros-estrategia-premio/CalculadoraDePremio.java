package aula06.seguros;

import java.math.BigDecimal;

/**
 * Contrato de uma estratégia de cálculo de prêmio de seguro auto. Não existe
 * nenhuma hierarquia de "tipos de apólice" aqui — o que varia é apenas o
 * algoritmo aplicado sobre o mesmo par (perfil, valor segurado).
 */
public interface CalculadoraDePremio {
    BigDecimal calcular(PerfilDoCondutor perfil, BigDecimal valorSegurado);
}
